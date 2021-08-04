package com.chenxiaolani.lecmsend.controller;

import com.chenxiaolani.lecmsend.entity.Result;
import com.chenxiaolani.lecmsend.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AuthController {

    // 这是spring security 里面的接口
    private UserDetailsService userDetailsService;
    // 这是spring security 里面内置的类AuthenticationManager
    private AuthenticationManager authenticationManager;

    public AuthController(UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public String auth() {
        return "test";
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> request) {
        String username = request.get("username").toString();
        String password = request.get("password").toString();

        // 通过用户名去拿用户真正的密码
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new Result(false, "用户不存在", -1,null);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        // 让鉴权管理者去鉴权，有可能鉴权失败需要try catch
        try {
            // 比对密码
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);

            User loginInfo = new User(1, username);
            return new Result(true, "登录成功", 0,loginInfo);
        } catch (BadCredentialsException e) {
            return new Result(false, "登录失败", -1,null);
        }
    }

}
