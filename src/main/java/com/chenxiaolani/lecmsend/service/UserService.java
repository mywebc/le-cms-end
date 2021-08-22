package com.chenxiaolani.lecmsend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenxiaolani.lecmsend.entity.User;
import com.chenxiaolani.lecmsend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

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
