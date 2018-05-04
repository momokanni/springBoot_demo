package com.txhl.wxorder.dao;

import com.txhl.wxorder.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 类目数据库访问层
 * class_name: ProductCategoryDao
 * package: com.txhl.wxorder.dao
 * creat_date: 2018/3/29
 * creat_time: 16:01
 **/
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryLIst);
}
