package com.adms.admin.core.scheduler;

import com.adms.admin.core.conf.JobAdminConfig;
import com.adms.core.biz.ExecutorBiz;
import com.xxl.rpc.core.remoting.invoker.call.CallType;
import com.xxl.rpc.core.remoting.invoker.reference.XxlRpcReferenceBean;
import com.xxl.rpc.core.remoting.invoker.route.LoadBalance;
import com.xxl.rpc.core.remoting.net.impl.netty_http.client.NettyHttpClient;
import com.xxl.rpc.core.serialize.impl.HessianSerializer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xuxueli 2018-10-28 00:18:17
 */

@Slf4j
public class JobScheduler {

    public void init() throws Exception {
//        // admin registry monitor run
//        JobRegistryMonitorHelper.getInstance().start();
//
//        // admin monitor run
//        JobFailMonitorHelper.getInstance().start();
//
//        // admin trigger pool start
//        JobTriggerPoolHelper.toStart();
//
//        // admin log report start
//        JobLogReportHelper.getInstance().start();
//
//        // start-schedule
//        JobScheduleHelper.getInstance().start();

        log.info(">>>>>>>>> init datax-web admin success.");
    }


    public void destroy() throws Exception {

//        // stop-schedule
//        JobScheduleHelper.getInstance().toStop();
//
//        // admin log report stop
//        JobLogReportHelper.getInstance().toStop();
//
//        // admin trigger pool stop
//        JobTriggerPoolHelper.toStop();
//
//        // admin monitor stop
//        JobFailMonitorHelper.getInstance().toStop();
//
//        // admin registry stop
//        JobRegistryMonitorHelper.getInstance().toStop();

    }

    // ---------------------- executor-client ----------------------
    private static ConcurrentMap<String, ExecutorBiz> executorBizRepository = new ConcurrentHashMap<>();

    public static ExecutorBiz getExecutorBiz(String address) throws Exception {
        // valid
        if (address == null || address.trim().length() == 0) {
            return null;
        }

        // load-cache
        address = address.trim();
        ExecutorBiz executorBiz = executorBizRepository.get(address);
        if (executorBiz != null) {
            return executorBiz;
        }

        // set-cache
        XxlRpcReferenceBean referenceBean = new XxlRpcReferenceBean();
        referenceBean.setClient(NettyHttpClient.class);
        referenceBean.setSerializer(HessianSerializer.class);
        referenceBean.setCallType(CallType.SYNC);
        referenceBean.setLoadBalance(LoadBalance.ROUND);
        referenceBean.setIface(ExecutorBiz.class);
        referenceBean.setVersion(null);
        referenceBean.setTimeout(3000);
        referenceBean.setAddress(address);
        referenceBean.setAccessToken(JobAdminConfig.getAdminConfig().getAccessToken());
        referenceBean.setInvokeCallback(null);
        referenceBean.setInvokerFactory(null);

        executorBiz = (ExecutorBiz) referenceBean.getObject();

        executorBizRepository.put(address, executorBiz);
        return executorBiz;
    }

}
