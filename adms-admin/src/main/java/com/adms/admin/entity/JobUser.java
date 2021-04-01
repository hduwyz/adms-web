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
public class JobUser implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色：0-普通用户、1-管理员
     */
    private String role;

    /**
     * 权限：执行器ID列表，多个逗号分割
     */
    private String permission;


}
