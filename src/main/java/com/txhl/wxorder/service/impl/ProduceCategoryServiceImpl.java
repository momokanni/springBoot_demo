package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.dao.ProductCategoryDao;
import com.txhl.wxorder.entity.ProductCategory;
import com.txhl.wxorder.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目业务层
 *
 * @author Administrator
 * @create 2018-03-29 17:55
 */
@Service
public class ProduceCategoryServiceImpl implements ProductCategoryService{

    @Autowired
    private ProductCategoryDao pcd;


    @Override
    public ProductCategory findOne(Integer categoryId) {
        return pcd.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findALL() {
        return pcd.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeLists) {
        return pcd.findByCategoryTypeIn(categoryTypeLists);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return pcd.save(productCategory);
    }
}
