package com.adms.executor.config;

import com.adms.core.executor.impl.JobSpringExecutor;
import com.adms.executor.util.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangyz
 * @date 2021年03月25日 17:06
 */
@Configuration
public class ExecutorConfig {

    private Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    private static final String DEFAULT_LOG_PATH = "log/executor/jobhandler";

    @Value("${adms.job.admin.addresses}")
    private String adminAddresses;

    @Value("${adms.job.executor.appname}")
    private String appName;

    @Value("${adms.job.executor.ip}")
    private String ip;

    @Value("${adms.job.executor.port}")
    private int port;

    @Value("${adms.job.accessToken}")
    private String accessToken;

    @Value("${adms.job.executor.logpath}")
    private String logPath;

    @Value("${adms.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Bean
    public JobSpringExecutor JobExecutor() {
        logger.info(">>>>>>>>>>> datax-web config init.");
        JobSpringExecutor jobSpringExecutor = new JobSpringExecutor();
        jobSpringExecutor.setAdminAddresses(adminAddresses);
        jobSpringExecutor.setAppName(appName);
        jobSpringExecutor.setIp(ip);
        jobSpringExecutor.setPort(port);
        jobSpringExecutor.setAccessToken(accessToken);
        String dataXHomePath = SystemUtils.getDataXHomePath();
        if (StringUtils.isEmpty(logPath)) {
            logPath = dataXHomePath + DEFAULT_LOG_PATH;
        }
        jobSpringExecutor.setLogPath(logPath);
        jobSpringExecutor.setLogRetentionDays(logRetentionDays);

        return jobSpringExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */
}
