package com.txhl.wxorder.dao;

import com.txhl.wxorder.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao pcd;
    
    /**
     * 查询
     * creat_date: 2018/3/29
     * creat_time: 17:03
     **/
    @Test
    @Transactional
    public void findOne(){
        ProductCategory productCategory = pcd.getOne(1);
        log.info(productCategory.toString());
    }

    /**
     * 插入
     * creat_date: 2018/3/29
     * creat_time: 17:02
     **/
    @Test
    @Transactional
    public void addTest(){
        ProductCategory productCategory = new ProductCategory("男生最爱",4);
        ProductCategory prod = pcd.save(productCategory);
        Assert.assertNotNull(prod);

    }

    /**
     * 修改
     * param:
     * describe: TODO
     * creat_user: TODO
     * creat_date: 2018/3/29
     * creat_time: 17:03
     **/
    @Test
    @Transactional
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory("女生最爱",2);
        productCategory.setCategoryType(3);
        pcd.saveAndFlush(productCategory);
    }


    @Test
    @Transactional
    public void findByCategoryTypeIn(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result = pcd.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }


}