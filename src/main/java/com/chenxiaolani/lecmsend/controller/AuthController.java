package com.chenxiaolani.lecmsend.controller;

import com.chenxiaolani.lecmsend.entity.Result;
import com.chenxiaolani.lecmsend.entity.User;
import com.chenxiaolani.lecmsend.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

    @PostMapping("/auth/login")
    public Result login(@RequestBody Map<String, Object> request) {
        String username = request.get("username").toString();
        String password = request.get("password").toString();

        // 交给shrio
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            token.setRememberMe(true);
            SecurityUtils.getSubject().login(token);
            return new Result(true, "登录成功", 0, null);
        } catch (UnknownAccountException e) {
            return new Result(false, "用户名错误", 0, null);
        } catch (IncorrectCredentialsException e) {
            return new Result(false, "密码错误", 0, null);
        }
    }

    @RequestMapping("/auth/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result(true, "退出成功", 0, null);
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
