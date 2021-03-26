package com.adms.core.handler;

import com.adms.core.biz.model.ReturnT;
import com.adms.core.biz.model.TriggerParam;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wangyz
 * @date 2021年03月25日 15:19
 */
public abstract class IJobHandler {

    /** success */
    public static final ReturnT<String> SUCCESS = new ReturnT<>(200, null);
    /** fail */
    public static final ReturnT<String> FAIL = new ReturnT<>(500, null);
    /** fail timeout */
    public static final ReturnT<String> FAIL_TIMEOUT = new ReturnT<>(502, null);

    public static final ConcurrentMap<String, String> jobTmpFiles = new ConcurrentHashMap<>();
    /**
     * execute handler, invoked when executor receives a scheduling request
     *
     * @param tgParam
     * @return
     * @throws Exception
     */
    public abstract ReturnT<String> execute(TriggerParam tgParam) throws Exception;

    /**
     * init handler, invoked when JobThread init
     */
    public void init() {
        // do something
    }


    /**
     * destroy handler, invoked when JobThread destroy
     */
    public void destroy() {
        // do something
    }
}
