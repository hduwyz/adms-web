package com.adms.admin.entity;

import lombok.Data;

/**
 * @author wangyz
 * @date 2021年04月08日 14:05
 */
@Data
public class LoginUser {
    private String username;
    private String password;
    private Integer rememberMe;
}
