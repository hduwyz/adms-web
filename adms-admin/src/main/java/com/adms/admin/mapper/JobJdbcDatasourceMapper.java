package com.adms.admin.mapper;

import com.adms.admin.entity.JobJdbcDatasource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * jdbc数据源配置 Mapper 接口
 * </p>
 *
 * @author wangyz
 * @since 2021-04-01
 */
public interface JobJdbcDatasourceMapper extends BaseMapper<JobJdbcDatasource> {
    int update(JobJdbcDatasource datasource);
}
