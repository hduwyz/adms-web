package com.adms.admin.mapper;

import com.adms.admin.entity.JobLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangyz
 * @since 2021-04-01
 */
public interface JobLogMapper extends BaseMapper<JobLog> {

    JobLog load(@Param("id") long id);

    long save(JobLog jobLog);

    int updateTriggerInfo(JobLog jobLog);

}
