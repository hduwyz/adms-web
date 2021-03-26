package com.adms.executor.service;

import com.adms.core.biz.model.ReturnT;
import com.adms.core.biz.model.TriggerParam;
import com.adms.core.handler.IJobHandler;
import com.adms.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangyz
 * @date 2021年03月26日 8:39
 */
@Slf4j
@JobHandler(value = "alg_1")
@Component
public class ExecutorJobHandler extends IJobHandler {
    @Override
    public ReturnT<String> execute(TriggerParam tgParam) throws Exception {
        log.info("开始执行算法=======================");
        Thread.sleep(200000);
        log.info("算法执行结束=======================");
        return ReturnT.SUCCESS;
    }
}
