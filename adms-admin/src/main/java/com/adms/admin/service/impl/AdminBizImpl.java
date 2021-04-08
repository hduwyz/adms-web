package com.adms.admin.service.impl;

import com.adms.admin.entity.JobLog;
import com.adms.admin.mapper.JobInfoMapper;
import com.adms.admin.mapper.JobLogMapper;
import com.adms.core.biz.AdminBiz;
import com.adms.core.biz.model.HandleCallbackParam;
import com.adms.core.biz.model.HandleProcessCallbackParam;
import com.adms.core.biz.model.RegistryParam;
import com.adms.core.biz.model.ReturnT;
import com.adms.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangyz
 * @date 2021年04月01日 15:23
 */
@Service
@Slf4j
public class AdminBizImpl implements AdminBiz {

    @Resource
    public JobLogMapper jobLogMapper;
    @Resource
    private JobInfoMapper jobInfoMapper;
//    @Resource
//    private JobRegistryMapper jobRegistryMapper;

    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        for (HandleCallbackParam handleCallbackParam : callbackParamList) {
            ReturnT<String> callbackResult = callback(handleCallbackParam);
            log.debug(">>>>>>>>> JobApiController.callback {}, handleCallbackParam={}, callbackResult={}",
                    (callbackResult.getCode() == IJobHandler.SUCCESS.getCode() ? "success" : "fail"), handleCallbackParam, callbackResult);
        }

        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> processCallback(List<HandleProcessCallbackParam> processCallbackParamList) {
        return null;
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return null;
    }

    private ReturnT<String> callback(HandleCallbackParam handleCallbackParam) {
        // valid log item
        JobLog log = jobLogMapper.load(handleCallbackParam.getLogId());
        if (log == null){
            return new ReturnT<String>(ReturnT.FAIL_CODE, "log item not found.");
        }
        if (log.getHandleCode() > 0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "log repeate callback.");     // avoid repeat callback, trigger child job etc
        }
        // trigger success, to trigger child job
        String callbackMsg = null;
        int resultCode = handleCallbackParam.getExecuteResult().getCode();
        return ReturnT.SUCCESS;
    }
}
