package com.adms.core.biz.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangyz
 * @date 2021年03月25日 14:52
 */
@Data
@ToString
public class TriggerParam implements Serializable {
    private static final long serialVersionUID = 7525819100022865724L;

    /**
     *任务id
     */
    private int jobId;

    /**
     *处理类
     */
    private String executorHandler;

    /**
     *入参
     */
    private String executorParams;
    /**
     *执行策略
     */
    private String executorBlockStrategy;
    /**
     *执行超时时间
     */
    private int executorTimeout;
    /**
     *日志id
     */
    private long logId;

    private long logDateTime;

    private String glueType;
    private String glueSource;
    private long glueUpdatetime;

    private int broadcastIndex;
    private int broadcastTotal;

    private String jobJson;
    private String processId;

    private String replaceParam;
    private String jvmParam;
    private Date startTime;
    private Date triggerTime;

    private String partitionInfo;

    private long startId;
    private long endId;

    private Integer incrementType;

    private String replaceParamType;
}
