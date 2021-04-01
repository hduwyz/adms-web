package com.adms.admin.controller;

import com.adms.admin.core.conf.JobAdminConfig;
import com.adms.admin.core.util.JacksonUtil;
import com.adms.core.biz.AdminBiz;
import com.adms.core.biz.model.RegistryParam;
import com.adms.core.biz.model.ReturnT;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wangyz
 * @date 2021年04月01日 15:13
 */
@RestController
@RequestMapping("/api")
public class JobApiController {

    @Resource
    private AdminBiz adminBiz;

    /**
     * registry
     *
     * @param data
     * @return
     */
    @RequestMapping("/registry")
    public ReturnT<String> registry(HttpServletRequest request, @RequestBody(required = false) String data) {
//        // valid
//        if (JobAdminConfig.getAdminConfig().getAccessToken()!=null
//                && JobAdminConfig.getAdminConfig().getAccessToken().trim().length()>0
//                && !JobAdminConfig.getAdminConfig().getAccessToken().equals(request.getHeader(JobRemotingUtil.XXL_RPC_ACCESS_TOKEN))) {
//            return new ReturnT<String>(ReturnT.FAIL_CODE, "The access token is wrong.");
//        }

        // param
        RegistryParam registryParam = null;
        try {
            registryParam = JacksonUtil.readValue(data, RegistryParam.class);
        } catch (Exception e) {}
        if (registryParam == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "The request data invalid.");
        }

        // invoke
        return adminBiz.registry(registryParam);
    }
}
