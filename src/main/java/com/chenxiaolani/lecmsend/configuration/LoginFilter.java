package com.chenxiaolani.lecmsend.configuration;

import com.alibaba.fastjson.JSONObject;
import com.chenxiaolani.lecmsend.entity.Result;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 覆盖shrio的重定向方法
 */
public class LoginFilter extends UserFilter {
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(new Result(false, "请先登录", -1, null)));
    }
}
