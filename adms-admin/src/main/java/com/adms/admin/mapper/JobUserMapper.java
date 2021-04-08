package com.adms.admin.mapper;

import com.adms.admin.entity.JobUser;
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
public interface JobUserMapper extends BaseMapper<JobUser> {

    JobUser loadByUserName(@Param("username") String username);
}
