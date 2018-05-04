package com.txhl.wxorder.service;

import com.txhl.wxorder.entity.ProductCategory;

import java.util.List;

/**
 * class_name: ProductCategoryService
 * package: com.txhl.wxorder.service
 * describe: 类目
 * creat_user:
 * creat_date: 2018/4/4
 * creat_time: 14:52
 **/
public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findALL();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeLists);

    ProductCategory save(ProductCategory productCategory);

}
