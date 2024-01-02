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

import javax.swing.*;
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
    public Result queryList(PageParam pageParam,
                            @RequestParam(required=false) String productName,
                            @RequestParam(required=false) String productPrice,
                            @RequestParam(required=false) String productType){
        System.out.println(pageParam);
        System.out.println(productName);
        System.out.println(productPrice);
        System.out.println(productType);
        PageHelper.startPage(pageParam.getPage(), pageParam.getSize());
        List<Product> res = productMapper.queryList(productName, productPrice, productType);
        PageInfo pageInfo = new PageInfo<>(res);
        return Result.ok().data("result", pageInfo);
    }

    @ApiOperation("新增产品")
    @PostMapping("/addProduct")
    public Result addProduct(@RequestBody Product product){
        System.out.println(product);
        Integer res = productMapper.addProduct(product);
        if (res == 1){
            return Result.ok().data("info", "添加成功");
        } else {
            return Result.error().data("info", "添加失败");
        }

    }

    @ApiOperation("修改产品")
    @PostMapping("/changeProduct")
    public Result changeProduct(@RequestBody Product product){
        System.out.println(product);
        Integer res = productMapper.changeProduct(product);
        if (res == 1){
            return Result.ok().data("info", "修改成功");
        } else {
            return Result.error().data("info", "修改失败");
        }
    }

    @ApiOperation("删除产品")
    @PostMapping("/deleteProduct")
    public Result deleteProduct(@RequestBody Product product){
        Integer res = productMapper.deleteProduct(product);
        if (res == 1){
            return Result.ok().data("info", "删除成功");
        } else {
            return Result.error().data("info", "删除失败");
        }
    }
}
