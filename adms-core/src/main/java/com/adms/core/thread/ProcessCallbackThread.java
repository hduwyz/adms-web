package com.adms.core.thread;

import com.adms.core.biz.AdminBiz;
import com.adms.core.biz.model.HandleProcessCallbackParam;
import com.adms.core.biz.model.ReturnT;
import com.adms.core.enums.RegistryConfig;
import com.adms.core.executor.JobExecutor;
import com.adms.core.log.JobFileAppender;
import com.adms.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyz
 * @date 2021年04月01日 9:50
 */
@Slf4j
public class ProcessCallbackThread {

    private static ProcessCallbackThread instance = new ProcessCallbackThread();

    public static ProcessCallbackThread getInstance() {
        return instance;
    }

    /**
     * job results callback queue
     */
    private LinkedBlockingQueue<HandleProcessCallbackParam> callBackQueue = new LinkedBlockingQueue<>();

    public static void pushCallBack(HandleProcessCallbackParam callback) {
        getInstance().callBackQueue.add(callback);
        log.debug(">>>>>>>>>>> datax-web, push process callback request, logId:{}", callback.getLogId());
    }

    /**
     * callback thread
     */
    private Thread processCallbackThread;
    private Thread processRetryCallbackThread;
    private volatile boolean toStop = false;

    public void start(){
        if (JobExecutor.getAdminBizList() == null){
            log.info(">>>>>>>>>>> datax-web, executor callback config fail, adminAddresses is null.");
            return;
        }

        //callback
        processCallbackThread = new Thread(() -> {
            //正常的回调
            while(!toStop){
                try {
                    //take() 获取一个，为空则阻塞
                    HandleProcessCallbackParam callback = getInstance().callBackQueue.take();

                    List<HandleProcessCallbackParam> callbackParamList = new ArrayList<>();
                    //批量获取，为空不阻塞
                    int drainToNum = getInstance().callBackQueue.drainTo(callbackParamList);
                    callbackParamList.add(callback);

                    if (callbackParamList.size() > 0){
                        //处理回调
                        doCallback(callbackParamList);
                    }
                }catch (Exception e){
                    if (!toStop){
                        log.error(e.getMessage(), e);
                    }
                }
                // last callback
                try {
                    List<HandleProcessCallbackParam> callbackParamList = new ArrayList<HandleProcessCallbackParam>();
                    int drainToNum = getInstance().callBackQueue.drainTo(callbackParamList);
                    if (callbackParamList != null && callbackParamList.size() > 0) {
                        doCallback(callbackParamList);
                    }
                } catch (Exception e) {
                    if (!toStop) {
                        log.error(e.getMessage(), e);
                    }
                }
                log.info(">>>>>>>>>>> datax-web, executor callback thread destory.");
            }
        });
        processCallbackThread.setDaemon(true);
        processCallbackThread.setName("datax-web, executor TriggerCallbackThread");
        processCallbackThread.start();

        // retry
        processRetryCallbackThread = new Thread(() -> {
            while (!toStop) {
                try {
                    retryFailCallbackFile();
                } catch (Exception e) {
                    if (!toStop) {
                        log.error(e.getMessage(), e);
                    }

                }
                try {
                    TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
                } catch (InterruptedException e) {
                    if (!toStop) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
            log.info(">>>>>>>>>>> datax-web, executor retry callback thread destory.");
        });
        processRetryCallbackThread.setDaemon(true);
        processRetryCallbackThread.start();
    }

    public void toStop() {
        toStop = true;
        // stop callback, interrupt and wait
        if (processCallbackThread != null) {    // support empty admin address
            processCallbackThread.interrupt();
            try {
                processCallbackThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

        // stop retry, interrupt and wait
        if (processRetryCallbackThread != null) {
            processRetryCallbackThread.interrupt();
            try {
                processRetryCallbackThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

    }

    private void doCallback(List<HandleProcessCallbackParam> callbackParamList){
        boolean callbackRet = false;
        //回调，错误则重试
        for (AdminBiz adminBiz : JobExecutor.getAdminBizList()){
            try {
                ReturnT<String> callbackResult = adminBiz.processCallback(callbackParamList);
                if (callbackResult != null && ReturnT.SUCCESS_CODE == callbackResult.getCode()) {
                    log.info("datax-web job callback finish.");
                    callbackRet = true;
                    break;
                } else {
                    log.info("datax-web job callback fail, callbackResult:{}", callbackResult);
                    log.info("datax-web job callback finish.");
                }
            }catch (Exception e){
                log.info("datax-web job callback error", e);
            }
        }
        if (!callbackRet){
            appendFailCallbackFile(callbackParamList);
        }
    }

    // ---------------------- fail-callback file ----------------------
    private static String failCallbackFilePath = JobFileAppender.getLogPath().concat(File.separator).concat("processcallbacklog").concat(File.separator);
    private static String failCallbackFileName = failCallbackFilePath.concat("datax-web-processcallback-{x}").concat(".log");

    private void appendFailCallbackFile(List<HandleProcessCallbackParam> handleProcessCallbackParams) {
        // valid
        if (handleProcessCallbackParams == null || handleProcessCallbackParams.size() == 0) {
            return;
        }

        // append file
        byte[] callbackParamList_bytes = JobExecutor.getSerializer().serialize(handleProcessCallbackParams);

        File callbackLogFile = new File(failCallbackFileName.replace("{x}", String.valueOf(System.currentTimeMillis())));
        if (callbackLogFile.exists()) {
            for (int i = 0; i < 100; i++) {
                callbackLogFile = new File(failCallbackFileName.replace("{x}", String.valueOf(System.currentTimeMillis()).concat("-").concat(String.valueOf(i))));
                if (!callbackLogFile.exists()) {
                    break;
                }
            }
        }
        FileUtil.writeFileContent(callbackLogFile, callbackParamList_bytes);
    }

    private void retryFailCallbackFile() {

        // valid
        File callbackLogPath = new File(failCallbackFilePath);
        if (!callbackLogPath.exists()) {
            return;
        }
        if (callbackLogPath.isFile()) {
            callbackLogPath.delete();
        }
        if (!(callbackLogPath.isDirectory() && callbackLogPath.list() != null && callbackLogPath.list().length > 0)) {
            return;
        }

        // load and clear file, retry
        List<HandleProcessCallbackParam> params;
        for (File f : callbackLogPath.listFiles()) {
            byte[] ps = FileUtil.readFileContent(f);
            params = (List<HandleProcessCallbackParam>) JobExecutor.getSerializer().deserialize(ps, HandleProcessCallbackParam.class);
            f.delete();
            doCallback(params);
        }
    }
}
