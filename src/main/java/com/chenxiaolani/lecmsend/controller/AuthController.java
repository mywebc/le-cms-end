package com.chenxiaolani.lecmsend.controller;

import com.chenxiaolani.lecmsend.entity.Result;
import com.chenxiaolani.lecmsend.entity.User;
import com.chenxiaolani.lecmsend.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;


@RestController
public class AuthController {

    private UserService userService;

    @Inject
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth")
    public Result auth() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.contains("anonymous")) {
            return new Result(false, "请先登录", -1, null);
        }
        return new Result(true, null, 0, userService.getUserInfoByUsername((username)));
    }

    @PostMapping("/auth/login")
    public Result login(@RequestBody Map<String, Object> request) {
        String username = request.get("username").toString();
        String password = request.get("password").toString();

        // 交给shrio
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        token.setRememberMe(true);
//        SecurityUtils.getSubject().login(token);
        return null;
    }

    @PostMapping("/auth/register")
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

    //测试权限
    @GetMapping("/userInfo")
    @ResponseBody
    public Result getUserInfo() {
        return new Result(true, "获取成功", 0, null);
    }
}
