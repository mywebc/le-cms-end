package com.chenxiaolani.lecmsend.controller;

import com.chenxiaolani.lecmsend.entity.Result;
import com.chenxiaolani.lecmsend.entity.User;
import com.chenxiaolani.lecmsend.jwt.JwtTokenProvider;
import com.chenxiaolani.lecmsend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Controller
public class AuthController {
    // 这是spring security 里面的接口
    private UserDetailsService userDetailsService;
    // 这是spring security 里面内置的类AuthenticationManager
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Inject
    public AuthController(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Result auth() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.contains("anonymous")) {
            return new Result(false, "请先登录", -1, null);
        }
        return new Result(true, null, 0, userService.getUserInfoByUsername((username)));
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
            if (!bCryptPasswordEncoder.matches(password, userInfo.getPassword())) {
                return new Result(false, "密码错误", 0, null);
            }
            // 生成令牌
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            try {
                // 进行鉴权
//                authenticationManager.authenticate(token);
                // 上下文存储下来
//                SecurityContextHolder.getContext().setAuthentication(token);
                System.out.println("getusername" + username);
                List<String> roles = Stream.of("admin", "root").collect(toList());
                String token = jwtTokenProvider.createToken(username, roles);
                return new Result(true, "登录成功", 0, userInfo, token);
            } catch (AuthenticationException e) {
                return new Result(false, "登录失败", -1, null);
            }
        }
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
            User registerUserInfo = userService.registerUser(username, bCryptPasswordEncoder.encode(password));
            return new Result(true, "注册成功", 0, registerUserInfo);
        }
    }
}
