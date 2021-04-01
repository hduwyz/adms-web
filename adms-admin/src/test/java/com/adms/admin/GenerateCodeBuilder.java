package com.adms.admin;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenerateCodeBuilder {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    //作者
    private final String author = "wangyz";

    //输出路径(设定为项目文件目录，二次生成会覆盖目标文件)
    private final String outputdir = "D:/generator/";

    //包名称
    private final String packageName = "com.adms.admin";

    @Test
    public void testGenerate() {

        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setUrl(this.url)
                .setUsername(this.username)
                .setPassword(this.password)
                .setDriverName(this.driver);

        // 策略，
        StrategyConfig strategyConfig = new StrategyConfig()
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
//                .setInclude("ckpi") // 表名，注掉会生成全部表
                .setSkipView(true);

        GlobalConfig config = new GlobalConfig()
                .setActiveRecord(false) // 继承mapper直接增删改查
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setEnableCache(false)
                .setAuthor(author)
                .setOutputDir(outputdir)
                .setFileOverride(true)
                .setDateType(DateType.ONLY_DATE)
//                .setMapperName("%sMapper")
//                .setServiceName("%sService")
                .setServiceImplName("%sService")
//                .setServiceImplName("%sService")
//                .setControllerName("%sController")
                .setOpen(true);


        PackageConfig packageConfig = new PackageConfig()
                .setParent(packageName)
                .setController("controller")
                .setEntity("entity")
                .setMapper("mapper")
                .setXml("mapper");

        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .execute();
    }
}

