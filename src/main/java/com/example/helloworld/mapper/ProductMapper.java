package com.example.helloworld.mapper;

import com.example.helloworld.entity.Product;
import com.example.helloworld.utils.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guantong
 */
public interface ProductMapper {

    /**
     * 查询全部产品
     * */
    public List<Product> queryList();



    /**
     * 添加产品
     */
    public Integer addProduct(@Param("product") Product product);

}
