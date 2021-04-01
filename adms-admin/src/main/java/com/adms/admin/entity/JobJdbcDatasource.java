package com.adms.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * jdbc数据源配置
 * </p>
 *
 * @author wangyz
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JobJdbcDatasource implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据源名称
     */
    private String datasourceName;

    /**
     * 数据源
     */
    private String datasource;

    /**
     * 数据源分组
     */
    private String datasourceGroup;

    /**
     * 数据库名
     */
    private String databaseName;

    /**
     * 用户名
     */
    private String jdbcUsername;

    /**
     * 密码
     */
    private String jdbcPassword;

    /**
     * jdbc url
     */
    private String jdbcUrl;

    /**
     * jdbc驱动类
     */
    private String jdbcDriverClass;

    private String zkAdress;

    /**
     * 状态：0删除 1启用 2禁用
     */
    private Boolean status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 备注
     */
    private String comments;


}
