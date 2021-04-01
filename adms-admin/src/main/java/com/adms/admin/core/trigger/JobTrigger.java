package com.adms.admin.core.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xxl-job trigger
 * Created by xuxueli on 17/7/13.
 */
public class JobTrigger {
    private static Logger logger = LoggerFactory.getLogger(JobTrigger.class);

    /**
     * trigger job
     *
     * @param jobId
     * @param triggerType
     * @param failRetryCount        >=0: use this param
     *                              <0: use param from job info config
     * @param executorShardingParam
     * @param executorParam         null: use job param
     *                              not null: cover job param
     */
    public static void trigger(int jobId, TriggerTypeEnum triggerType, int failRetryCount, String executorShardingParam, String executorParam) {


    }

}
