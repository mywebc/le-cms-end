package com.chenxiaolani.lecmsend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenxiaolani.lecmsend.entity.User;
import com.chenxiaolani.lecmsend.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserInfoByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User userInfo = userMapper.selectOne(wrapper);
        return userInfo;
    }

    public User registerUser(String username, String password) {
        User userInfo = new User(username, password);
        userMapper.insert(userInfo);
        return userInfo;
    }
}
