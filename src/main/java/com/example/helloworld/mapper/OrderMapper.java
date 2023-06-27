package com.example.helloworld.mapper;

import com.example.helloworld.entity.Order;

import java.util.List;


public interface OrderMapper {

    /**
     * 查询全部订单
     * */
    public List<Order>  findAll();

    /**
     * 查询某一学生的全部订单
     * */
    public List<Order>  findById(Integer id);

}
