package com.example.helloworld.controller;

import com.example.helloworld.entity.Product;
import com.example.helloworld.mapper.LoginMapper;
import com.example.helloworld.mapper.ProductMapper;
import com.example.helloworld.utils.PageParam;
import com.example.helloworld.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;


    @ApiOperation("查询所有产品信息")
    @GetMapping("/queryList")
    public Result queryList(PageParam pageParam){
        System.out.println(pageParam);
//        Map<String, Object> data = new HashMap();
//        data.put("currIndex", (pageParam.getPage()-1)*pageParam.getSize());
//        data.put("pageSize", pageParam.getSize());
        PageHelper.startPage(pageParam.getPage(), pageParam.getSize());
        List<Product> res = productMapper.queryList();
        PageInfo pageInfo = new PageInfo<>(res);
        return Result.ok().data("info", pageInfo);
    }

    @ApiOperation("新增产品")
    @PostMapping("/addProduct")
    public Result addProduct(@RequestBody Product product){
        System.out.println(product);
        Integer res = productMapper.addProduct(product);
        return Result.ok().data("info", res);
    }
}
