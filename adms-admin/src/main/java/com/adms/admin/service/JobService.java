package com.adms.admin.service;

import com.adms.admin.entity.JobInfo;
import com.adms.core.biz.model.ReturnT;

public interface JobService {

    /**
     * add job
     *
     * @param jobInfo
     * @return
     */
    ReturnT<String> add(JobInfo jobInfo);
}
