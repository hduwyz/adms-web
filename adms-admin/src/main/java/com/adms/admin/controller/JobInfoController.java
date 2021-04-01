package com.adms.admin.controller;

import com.adms.admin.core.thread.JobTriggerPoolHelper;
import com.adms.admin.core.trigger.TriggerTypeEnum;
import com.adms.admin.dto.TriggerJobDto;
import com.adms.admin.entity.JobInfo;
import com.adms.admin.service.JobService;
import com.adms.core.biz.model.ReturnT;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wangyz
 * @date 2021年03月26日 10:48
 */
@RestController
@RequestMapping("/api/job")
public class JobInfoController extends BaseController{

    @Resource
    private JobService jobService;

    @PostMapping("/add")
    public ReturnT<String> add(HttpServletRequest request, @RequestBody JobInfo jobInfo) {
        jobInfo.setUserId(getCurrentUserId(request));
        return jobService.add(jobInfo);
    }

    @PostMapping(value = "/trigger")
    public ReturnT<String> triggerJob(@RequestBody TriggerJobDto dto) {
        // force cover job param
        String executorParam=dto.getExecutorParam();
        if (executorParam == null) {
            executorParam = "";
        }
        JobTriggerPoolHelper.trigger(dto.getJobId(), TriggerTypeEnum.MANUAL, -1, null, executorParam);
        return ReturnT.SUCCESS;
    }
}
