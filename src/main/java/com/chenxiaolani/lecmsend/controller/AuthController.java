package com.chenxiaolani.lecmsend.controller;

import com.chenxiaolani.lecmsend.entity.Result;
import com.chenxiaolani.lecmsend.entity.User;
import com.chenxiaolani.lecmsend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {

    // 这是spring security 里面的接口
    private UserDetailsService userDetailsService;
    // 这是spring security 里面内置的类AuthenticationManager
    private AuthenticationManager authenticationManager;

    private UserService userService;

    @Inject
    public AuthController(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
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

        User userInfo = userService.getUserInfoByUsername(username);
        if (userInfo == null) {
            return new Result(false, "用户不存在", 0, userInfo);
        } else {
            if(!userInfo.getPassword().equals(password)) {
                return new Result(false, "密码错误", 0, null);
            }
            return new Result(true, "登录成功", 0, userInfo);
        }

//        // 通过用户名去拿用户真正的密码
//        UserDetails userDetails = null;
//        try {
//            userDetails = userDetailsService.loadUserByUsername(username);
//        } catch (UsernameNotFoundException e) {
//            return new Result(false, "用户不存在", -1, null);
//        }
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//
//        // 让鉴权管理者去鉴权，有可能鉴权失败需要try catch
//        try {
//            // 比对密码
//            authenticationManager.authenticate(token);
//            SecurityContextHolder.getContext().setAuthentication(token);
//
//            User loginInfo = new User(username, password);
//            return new Result(true, "登录成功", 0, loginInfo);
//        } catch (BadCredentialsException e) {
//            return new Result(false, "登录失败", -1, null);
//        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, Object> request) {
        String username = request.get("username").toString();
        String password = request.get("password").toString();

        User userInfo = userService.getUserInfoByUsername(username);
        if (userInfo != null) {
            return new Result(false, "用户已存在", 0, userInfo);
        } else {
            User registerUserInfo = userService.registerUser(username, password);
            return new Result(true, "注册成功", 0, registerUserInfo);
        }
    }
}
