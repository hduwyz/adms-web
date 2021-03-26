package com.adms.core.biz.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wangyz
 * @date 2021年03月25日 14:16
 */
@Data
@ToString
public class ReturnT<T> implements Serializable {
    private static final long serialVersionUID = -7321570714616615623L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 200;

    public static final ReturnT<String> SUCCESS = new ReturnT(null);
    public static final ReturnT<String> FAIL = new ReturnT(FAIL_CODE, null);

    private int code;
    private String msg;
    private T content;

    public ReturnT(){}

    public ReturnT(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ReturnT(T content){
        this.code = SUCCESS_CODE;
        this.content = content;
    }

}
