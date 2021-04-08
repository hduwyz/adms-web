package com.adms.core.biz.impl;

import com.adms.core.biz.ExecutorBiz;
import com.adms.core.biz.model.LogResult;
import com.adms.core.biz.model.ReturnT;
import com.adms.core.biz.model.TriggerParam;
import com.adms.core.enums.ExecutorBlockStrategyEnum;
import com.adms.core.executor.JobExecutor;
import com.adms.core.handler.IJobHandler;
import com.adms.core.thread.JobThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangyz
 * @date 2021年03月25日 15:17
 */
public class ExecutorBizImpl implements ExecutorBiz {

    private static Logger logger = LoggerFactory.getLogger(ExecutorBizImpl.class);

    @Override
    public ReturnT<String> beat() {
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> idleBeat(int jobId) {

        return null;
    }

    @Override
    public ReturnT<String> kill(int jobId) {
        return null;
    }

    @Override
    public ReturnT<LogResult> log(long logDateTim, long logId, int fromLineNum) {
        return null;
    }

    @Override
    public ReturnT<String> run(TriggerParam triggerParam) {
        // load old：jobHandler + jobThread
        JobThread jobThread = JobExecutor.loadJobThread(triggerParam.getJobId());
        IJobHandler jobHandler = jobThread != null ? jobThread.getHandler() : null;
        String removeOldReason = null;

        // new jobhandler
        IJobHandler newJobHandler = JobExecutor.loadJobHandler(triggerParam.getExecutorHandler());

        // valid old jobThread
        if (jobThread != null && jobHandler != newJobHandler) {
            // change handler, need kill old thread
            removeOldReason = "change jobhandler or glue type, and terminate the old job thread.";

            jobThread = null;
            jobHandler = null;
        }

        // valid handler
        if (jobHandler == null) {
            jobHandler = newJobHandler;
            if (jobHandler == null) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "job handler [" + triggerParam.getExecutorHandler() + "] not found.");
            }
        }

        // executor block strategy
        if (jobThread != null) {
            ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(triggerParam.getExecutorBlockStrategy(), null);
            if (ExecutorBlockStrategyEnum.DISCARD_LATER == blockStrategy) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    return new ReturnT<>(ReturnT.FAIL_CODE, "block strategy effect：" + ExecutorBlockStrategyEnum.DISCARD_LATER.getTitle());
                }
            } else if (ExecutorBlockStrategyEnum.COVER_EARLY == blockStrategy) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    removeOldReason = "block strategy effect：" + ExecutorBlockStrategyEnum.COVER_EARLY.getTitle();

                    jobThread = null;
                }
            } else {
                // just queue trigger
            }
        }

        // replace thread (new or exists invalid)
        if (jobThread == null) {
            jobThread = JobExecutor.registJobThread(triggerParam.getJobId(), jobHandler, removeOldReason);
        }

        // push data to queue
        ReturnT<String> pushResult = jobThread.pushTriggerQueue(triggerParam);
        return pushResult;
    }
}
