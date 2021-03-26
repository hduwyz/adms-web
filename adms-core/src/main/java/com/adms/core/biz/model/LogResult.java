package com.adms.core.biz.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangyz
 * @date 2021年03月25日 14:49
 */
@Data
public class LogResult implements Serializable {
    private static final long serialVersionUID = 8545846428748156164L;

    public LogResult(int fromLineNum, int toLineNum, String logContent, boolean isEnd) {
        this.fromLineNum = fromLineNum;
        this.toLineNum = toLineNum;
        this.logContent = logContent;
        this.isEnd = isEnd;
    }

    /**
     *开始行
     */
    private int fromLineNum;

    /**
     *结束行
     */
    private int toLineNum;

    /**
     *日志内容
     */
    private String logContent;

    /**
     *日志是否结束
     */
    private boolean isEnd;
}
