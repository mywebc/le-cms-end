package com.chenxiaolani.lecmsend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenxiaolani.lecmsend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Mapper
@Repository // 加上这个，解决依赖注入的红色警告
public interface UserMapper extends BaseMapper<User> {
}
