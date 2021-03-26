package com.adms.core.biz;

import com.adms.core.biz.model.HandleCallbackParam;
import com.adms.core.biz.model.HandleProcessCallbackParam;
import com.adms.core.biz.model.RegistryParam;
import com.adms.core.biz.model.ReturnT;

import java.util.List;

/**
 * @author wangyz
 * @date 2021年03月25日 14:14
 */
public interface AdminBiz {

    //------------------- callback ---------------------------------
    /**
     *
     * @author wangyz
     * @date 2021/3/25 14:30
     * @param callbackParamList
     * @return com.adms.core.biz.model.ReturnT<java.lang.String>
     */
    ReturnT<String> callback(List<HandleCallbackParam> callbackParamList);

    /**
     * processCallback
     *
     * @param processCallbackParamList
     * @return
     */
    ReturnT<String> processCallback(List<HandleProcessCallbackParam> processCallbackParamList);

    // ---------------------- registry ----------------------

    /**
     * registry服务注册
     *
     * @param registryParam
     * @return
     */
    ReturnT<String> registry(RegistryParam registryParam);

    /**
     * registry remove服务移除
     *
     * @param registryParam
     * @return
     */
    ReturnT<String> registryRemove(RegistryParam registryParam);


}
