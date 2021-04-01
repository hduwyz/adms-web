package com.adms.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyz
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JobExecutor implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 执行器AppName
     */
    private String appName;

    /**
     * 执行器名称
     */
    private String title;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    private Integer addressType;

    /**
     * 执行器地址列表，多地址逗号分隔
     */
    private String addressList;


}
