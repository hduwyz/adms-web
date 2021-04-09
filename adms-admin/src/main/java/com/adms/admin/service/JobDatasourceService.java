package com.adms.admin.service;

import com.adms.admin.entity.JobJdbcDatasource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * jdbc数据源配置表服务接口
 *
 * @author jingwk
 * @version v2.0
 * @since 2020-01-10
 */
public interface JobDatasourceService extends IService<JobJdbcDatasource> {
    /**
     * 测试数据源
     * @param jdbcDatasource
     * @return
     */
    Boolean dataSourceTest(JobJdbcDatasource jdbcDatasource) throws IOException;

    /**
     *更新数据源信息
     * @param datasource
     * @return
     */
    int update(JobJdbcDatasource datasource);

    /**
     * 获取所有数据源
     * @return
     */
    List<JobJdbcDatasource> selectAllDatasource();
}