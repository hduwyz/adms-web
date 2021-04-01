package com.adms.admin.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author wangyz
 * @date 2021年03月26日 11:02
 */
public class BaseController extends ApiController {

    public Integer getCurrentUserId(HttpServletRequest request) {
        Enumeration<String> auth = request.getHeaders("");
        String token = auth.nextElement().replace("", "");
        return Integer.valueOf(token);
    }
}
