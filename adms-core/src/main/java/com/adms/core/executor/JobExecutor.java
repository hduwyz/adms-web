package com.adms.core.executor;

import com.adms.core.biz.AdminBiz;
import com.adms.core.biz.ExecutorBiz;
import com.adms.core.biz.client.AdminBizClient;
import com.adms.core.biz.impl.ExecutorBizImpl;
import com.adms.core.handler.IJobHandler;
import com.adms.core.thread.ExecutorRegistryThread;
import com.adms.core.thread.JobThread;
import com.adms.core.thread.ProcessCallbackThread;
import com.adms.core.thread.TriggerCallbackThread;
import com.xxl.rpc.core.registry.Register;
import com.xxl.rpc.core.remoting.net.impl.netty_http.server.NettyHttpServer;
import com.xxl.rpc.core.remoting.provider.XxlRpcProviderFactory;
import com.xxl.rpc.core.serialize.Serializer;
import com.xxl.rpc.core.serialize.impl.HessianSerializer;
import com.xxl.rpc.core.util.IpUtil;
import com.xxl.rpc.core.util.NetUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wangyz
 * @date 2021年03月25日 15:23
 */
@Data
public class JobExecutor {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutor.class);

    // ---------------------- param ----------------------
    private String adminAddresses;
    private String appName;
    private String ip;
    private int port;
    private String accessToken;
    private String logPath;
    private int logRetentionDays;

    // ---------------------- start + stop ----------------------
    public void start() throws Exception {
        // init logpath
        //init invoker, admin-client
        initAdminBizList(adminAddresses, accessToken);
        // init JobLogFileCleanThread
        // init TriggerCallbackThread
        TriggerCallbackThread.getInstance().start();
        // init ProcessCallbackThread
        ProcessCallbackThread.getInstance().start();
        // init executor-server
        port = port > 0 ? port : NetUtil.findAvailablePort(9999);
        ip = (ip != null && ip.trim().length() > 0) ? ip : IpUtil.getIp();
        initRpcProvider(ip, port, appName, accessToken);
    }

    public void destroy() {
        // destory executor-server
        stopRpcProvider();
        // destory jobThreadRepository
        if (jobThreadRepository.size() > 0) {
            for (Map.Entry<Integer, JobThread> item : jobThreadRepository.entrySet()) {
                removeJobThread(item.getKey(), "web container destroy and kill the job.");
                JobThread oldJobThread = removeJobThread(item.getKey(), "web container destroy and kill the job.");
                // wait for job thread push result to callback queue
                if (oldJobThread != null) {
                    try {
                        oldJobThread.join();
                    } catch (InterruptedException e) {
                        logger.error(">>>>>>>>>>> datax-web, JobThread destroy(join) error, jobId:{}", item.getKey(), e);
                    }
                }
            }
            jobThreadRepository.clear();
        }
        jobHandlerRepository.clear();
        // destory JobLogFileCleanThread
        // destory TriggerCallbackThread
        TriggerCallbackThread.getInstance().toStop();
        // destory ProcessCallbackThread
        ProcessCallbackThread.getInstance().toStop();
    }

    // ---------------------- admin-client (rpc invoker) ----------------------
    private static List<AdminBiz> adminBizList;
    private static Serializer serializer = new HessianSerializer();

    public static List<AdminBiz> getAdminBizList(){
        return adminBizList;
    }

    public static Serializer getSerializer(){
        return serializer;
    }

    private void initAdminBizList(String adminAddresses, String accessToken){
        if (adminAddresses != null && adminAddresses.trim().length()>0){
            for (String address : adminAddresses.trim().split(",")){
                if (address != null && address.trim().length()>0){
                    //实例化AdminBizClient
                    AdminBiz adminBiz = new AdminBizClient(address.trim(), accessToken);
                    if (adminBizList == null){
                        adminBizList = new ArrayList<>();
                    }
                    adminBizList.add(adminBiz);
                }
            }
        }
    }

    // ---------------------- executor-server (rpc provider) ----------------------
    private XxlRpcProviderFactory xxlRpcProviderFactory = null;

    private void initRpcProvider(String ip, int port, String appName, String accessToken) throws Exception {
        String address = IpUtil.getIpPort(ip, port);
        Map<String, String> serviceRegistryParam = new HashMap<>();
        serviceRegistryParam.put("appName", appName);
        serviceRegistryParam.put("address", address);

        xxlRpcProviderFactory = new XxlRpcProviderFactory();
        xxlRpcProviderFactory.setServer(NettyHttpServer.class);
        xxlRpcProviderFactory.setSerializer(HessianSerializer.class);
        xxlRpcProviderFactory.setCorePoolSize(20);
        xxlRpcProviderFactory.setMaxPoolSize(200);
        xxlRpcProviderFactory.setIp(ip);
        xxlRpcProviderFactory.setPort(port);
        xxlRpcProviderFactory.setAccessToken(accessToken);
        xxlRpcProviderFactory.setServiceRegistry(ExecutorServiceRegistry.class);
        xxlRpcProviderFactory.setServiceRegistryParam(serviceRegistryParam);

        // add services
        xxlRpcProviderFactory.addService(ExecutorBiz.class.getName(), null, new ExecutorBizImpl());
        // start
        xxlRpcProviderFactory.start();
    }

    public static class ExecutorServiceRegistry extends Register {

        @Override
        public void start(Map<String, String> map) {
            ExecutorRegistryThread.getInstance().start(map.get("appName"), map.get("address"));
        }

        @Override
        public void stop() {
            ExecutorRegistryThread.getInstance().toStop();
        }

        @Override
        public boolean registry(Set<String> set, String s) {
            return false;
        }

        @Override
        public boolean remove(Set<String> set, String s) {
            return false;
        }

        @Override
        public Map<String, TreeSet<String>> discovery(Set<String> set) {
            return null;
        }

        @Override
        public TreeSet<String> discovery(String s) {
            return null;
        }
    }

    private void stopRpcProvider() {
        // stop provider factory
        try {
            xxlRpcProviderFactory.stop();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // ---------------------- job handler repository ----------------------
    private static ConcurrentMap<String, IJobHandler> jobHandlerRepository = new ConcurrentHashMap<String, IJobHandler>();

    public static IJobHandler registJobHandler(String name, IJobHandler jobHandler) {
        logger.info(">>>>>>>>>>> datax-web register jobhandler success, name:{}, jobHandler:{}", name, jobHandler);
        return jobHandlerRepository.put(name, jobHandler);
    }

    public static IJobHandler loadJobHandler(String name) {
        return jobHandlerRepository.get(name);
    }

    // ---------------------- job thread repository ----------------------
    private static ConcurrentMap<Integer, JobThread> jobThreadRepository = new ConcurrentHashMap<Integer, JobThread>();

    public static JobThread registJobThread(int jobId, IJobHandler handler, String removeOldReason) {
        JobThread newJobThread = new JobThread(jobId, handler);
        newJobThread.start();
        logger.info(">>>>>>>>>>> datax-web regist JobThread success, jobId:{}, handler:{}", new Object[]{jobId, handler});

        JobThread oldJobThread = jobThreadRepository.put(jobId, newJobThread);    // putIfAbsent | oh my god, map's put method return the old value!!!
        if (oldJobThread != null) {
            oldJobThread.toStop(removeOldReason);
            oldJobThread.interrupt();
        }

        return newJobThread;
    }

    public static JobThread removeJobThread(int jobId, String removeOldReason) {
        JobThread oldJobThread = jobThreadRepository.remove(jobId);
        if (oldJobThread != null) {
            oldJobThread.toStop(removeOldReason);
            oldJobThread.interrupt();
            return oldJobThread;
        }
        return null;
    }

    public static JobThread loadJobThread(int jobId) {
        JobThread jobThread = jobThreadRepository.get(jobId);
        return jobThread;
    }
}
