package com.adms.core.biz.client;

import com.adms.core.biz.AdminBiz;
import com.adms.core.biz.model.HandleCallbackParam;
import com.adms.core.biz.model.HandleProcessCallbackParam;
import com.adms.core.biz.model.RegistryParam;
import com.adms.core.biz.model.ReturnT;
import com.adms.core.util.JobRemotingUtil;

import java.util.List;

/**
 * 客户端调用admin管理平台
 * @author wangyz
 * @date 2021年03月25日 14:56
 */
public class AdminBizClient implements AdminBiz {

    public AdminBizClient(){
    }

    public AdminBizClient(String addressUrl, String accessToken){
        this.accessToken=accessToken;
        this.addressUrl=addressUrl;
        if (!this.addressUrl.endsWith("/")){
            this.addressUrl = this.addressUrl + "/";
        }
    }

    private String addressUrl;
    private String accessToken;
    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return JobRemotingUtil.postBody(addressUrl+"api/callback", accessToken, callbackParamList, 3);
    }

    @Override
    public ReturnT<String> processCallback(List<HandleProcessCallbackParam> processCallbackParamList) {
        return JobRemotingUtil.postBody(addressUrl+"api/processCallback", accessToken, processCallbackParamList, 3);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return JobRemotingUtil.postBody(addressUrl+"api/registry", accessToken, registryParam, 3);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return JobRemotingUtil.postBody(addressUrl+"api/registryRemove", accessToken, registryParam, 3);
    }
}
