package com.txhl.wxorder.dao.impl;

import com.txhl.wxorder.entity.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * mybatis使用测试类
 *
 * @author Administrator
 * @create 2018-05-02 15:19
 */
@Repository
public class ProductCategoryDaoImpl {

    @Autowired
    ProductCategoryMapper mapper;

    public int insertByMap(Map<String,Object> map){
        return  mapper.insertByMap(map);
    }
}
