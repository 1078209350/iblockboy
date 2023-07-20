package com.example.helloworld.mapper;

import com.example.helloworld.entity.Order;
import com.example.helloworld.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginMapper {

    /**
     * 验证该用户是否存在
     */
    public List<User> selectUser(@Param("name") String name);

    /**
     * 登录
     * */
    public List<User> login(@Param("user") User user);

    /**
     * 注册
     * */
    public int register(@Param("user") User user);

}
