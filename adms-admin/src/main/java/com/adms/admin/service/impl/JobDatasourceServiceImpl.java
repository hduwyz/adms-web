package com.adms.admin.service.impl;

import com.adms.admin.entity.JobJdbcDatasource;
import com.adms.admin.mapper.JobJdbcDatasourceMapper;
import com.adms.admin.service.JobDatasourceService;
import com.adms.admin.util.AESUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by jingwk on 2020/01/30
 */
@Service
@Transactional(readOnly = true)
public class JobDatasourceServiceImpl extends ServiceImpl<JobJdbcDatasourceMapper, JobJdbcDatasource> implements JobDatasourceService {

    @Resource
    private JobJdbcDatasourceMapper datasourceMapper;

    @Override
    public Boolean  dataSourceTest(JobJdbcDatasource jobDatasource) throws IOException {
        String userName = AESUtil.decrypt(jobDatasource.getJdbcUsername());
        //  判断账密是否为密文
        if (userName == null) {
            jobDatasource.setJdbcUsername(AESUtil.encrypt(jobDatasource.getJdbcUsername()));
        }
        String pwd = AESUtil.decrypt(jobDatasource.getJdbcPassword());
        if (pwd == null) {
            jobDatasource.setJdbcPassword(AESUtil.encrypt(jobDatasource.getJdbcPassword()));
        }
//        BaseQueryTool queryTool = QueryToolFactory.getByDbType(jobDatasource);
//        return queryTool.dataSourceTest();
        return true;
    }

    @Override
    public int update(JobJdbcDatasource datasource) {
        return datasourceMapper.update(datasource);
    }

    @Override
    public List<JobJdbcDatasource> selectAllDatasource() {
        return datasourceMapper.selectList(null);
    }

}