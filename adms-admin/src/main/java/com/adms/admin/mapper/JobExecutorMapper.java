package com.adms.admin.mapper;

import com.adms.admin.entity.JobExecutor;
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
public interface JobExecutorMapper extends BaseMapper<JobExecutor> {

    JobExecutor load(@Param("id") int id);
}
