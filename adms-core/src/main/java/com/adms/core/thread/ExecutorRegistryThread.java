package com.adms.core.thread;

import com.adms.core.biz.AdminBiz;
import com.adms.core.biz.model.RegistryParam;
import com.adms.core.biz.model.ReturnT;
import com.adms.core.enums.RegistryConfig;
import com.adms.core.executor.JobExecutor;
import com.adms.core.util.OSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author wangyz
 * @date 2021年03月25日 16:00
 */
public class ExecutorRegistryThread {
    private static Logger logger = LoggerFactory.getLogger(ExecutorRegistryThread.class);

    private static ExecutorRegistryThread instance = new ExecutorRegistryThread();
    public static ExecutorRegistryThread getInstance(){
        return instance;
    }

    private Thread registryThread;
    private volatile boolean toStop = false;

    public void start(final String appName, final String address){
        // valid
        if (appName==null || appName.trim().length()==0) {
            logger.warn(">>>>>>>>>>> adms-web, executor registry config fail, appName is null.");
            return;
        }
        if (JobExecutor.getAdminBizList() == null) {
            logger.warn(">>>>>>>>>>> adms-web, executor registry config fail, adminAddresses is null.");
            return;
        }

        registryThread = new Thread(() -> {
            //registry
            while(!toStop){
                try{
                    RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appName, address,
                            OSUtils.cpuUsage(), OSUtils.memoryUsage(), OSUtils.loadAverage());
                    for (AdminBiz adminBiz : JobExecutor.getAdminBizList()){
                        try {
                            ReturnT<String> registryResult = adminBiz.registry(registryParam);
                            if (registryResult != null && ReturnT.SUCCESS_CODE == registryResult.getCode()){
                                registryResult = ReturnT.SUCCESS;
                                logger.debug(">>>>>>>>>>> adms-web registry success, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                                break;
                            }else {
                                logger.info(">>>>>>>>>>> adms-web registry fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                            }
                        }catch (Exception e) {
                            logger.info(">>>>>>>>>>> adms-web registry error, registryParam:{}", registryParam, e);
                        }
                    }
                }catch (Exception e) {
                    if (!toStop) {
                        logger.error(e.getMessage(), e);
                    }

                }
                try {
                    if (!toStop) {
                        TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
                    }
                } catch (InterruptedException e) {
                    if (!toStop) {
                        logger.warn(">>>>>>>>>>> adms-web, executor registry thread interrupted, error msg:{}", e.getMessage());
                    }
                }
            }
            // 当发生停止注册以后，registry remove
            try {
                RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appName, address);
                for (AdminBiz adminBiz: JobExecutor.getAdminBizList()) {
                    try {
                        ReturnT<String> registryResult = adminBiz.registryRemove(registryParam);
                        if (registryResult!=null && ReturnT.SUCCESS_CODE == registryResult.getCode()) {
                            registryResult = ReturnT.SUCCESS;
                            logger.info(">>>>>>>>>>> adms-web registry-remove success, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                            break;
                        } else {
                            logger.info(">>>>>>>>>>> adms-web registry-remove fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                        }
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.info(">>>>>>>>>>> adms-web registry-remove error, registryParam:{}", registryParam, e);
                        }

                    }

                }
            } catch (Exception e) {
                if (!toStop) {
                    logger.error(e.getMessage(), e);
                }
            }
            logger.info(">>>>>>>>>>> adms-web, executor registry thread destory.");
        });
        registryThread.setDaemon(true);
        registryThread.setName("adms-web, executor ExecutorRegistryThread");
        registryThread.start();
    }

    public void toStop() {
        toStop = true;
        // interrupt and wait
        registryThread.interrupt();
        try {
            registryThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
