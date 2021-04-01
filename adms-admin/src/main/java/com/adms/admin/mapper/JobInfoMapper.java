package com.adms.admin.mapper;

import com.adms.admin.entity.JobInfo;
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
public interface JobInfoMapper extends BaseMapper<JobInfo> {
    JobInfo loadById(@Param("id") int id);
}
