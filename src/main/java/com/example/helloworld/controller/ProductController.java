package com.example.helloworld.controller;

import com.example.helloworld.entity.Product;
import com.example.helloworld.mapper.LoginMapper;
import com.example.helloworld.mapper.ProductMapper;
import com.example.helloworld.utils.PageParam;
import com.example.helloworld.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;


//    @ApiOperation("查询所有产品信息")
//    @PostMapping("/queryList")
//    public Result getList(PageParam pageParam){
//        System.out.println(pageParam);
//        List<Product> res = productMapper.selectUser(user.getName());
//        if (res == null || res.size() == 0){
//            return Result.error().message("未查询到用户");
//        } else {
//            List<User> userList = loginMapper.login(user);
//            if (userList == null || userList.size() == 0){
//                return Result.error().message("密码有误，请重试！");
//            } else {
//                String token = JwtUtils.generateToken(user.getName());
//                return Result.ok().data("token", token).data("info", userList.get(0));
//            }
//        }
//    }
}
