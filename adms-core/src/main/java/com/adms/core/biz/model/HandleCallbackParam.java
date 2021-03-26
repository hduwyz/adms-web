package com.adms.core.biz.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wangyz
 * @date 2021年03月25日 14:43
 */
@Data
@ToString
public class HandleCallbackParam implements Serializable {
    private static final long serialVersionUID = 8649135756871379283L;

    private long logId;
    private long logDateTim;

    private ReturnT<String> executeResult;

    public HandleCallbackParam(){}
    public HandleCallbackParam(long logId, long logDateTim, ReturnT<String> executeResult) {
        this.logId = logId;
        this.logDateTim = logDateTim;
        this.executeResult = executeResult;
    }
}
