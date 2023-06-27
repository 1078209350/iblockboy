package com.example.helloworld.controller;

import com.example.helloworld.entity.Order;
import com.example.helloworld.mapper.OrderMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询全部订单
     * */
    @ApiOperation("查询全部学生")
    @GetMapping("/orderAll")
    public List<Order> queryOrderALl(){

        List<Order> list = orderMapper.findAll();
        System.out.println(list);
        return list;
    }
}
