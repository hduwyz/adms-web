package com.adms.core.biz.impl;

import com.adms.core.biz.ExecutorBiz;
import com.adms.core.biz.model.LogResult;
import com.adms.core.biz.model.ReturnT;
import com.adms.core.biz.model.TriggerParam;
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
        return null;
    }
}
