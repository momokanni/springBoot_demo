package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.entity.ProductCategory;
import com.txhl.wxorder.service.ProductCategoryService;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProduceCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService ps;

    @Test
    @Transactional
    public void findOne() {
        ProductCategory productCategory = ps.findOne(3);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
        log.info(productCategory.toString());
    }

    @Test
    @Transactional
    public void findALL() {
        List<ProductCategory> list = ps.findALL();
        Assert.assertNotEquals(0,list.size());
        log.info("类目条数：{}",list.size());
    }

    @Test
    @Transactional
    public void findByCategoryTypeIn() {
        List<ProductCategory> list = ps.findByCategoryTypeIn(Arrays.asList(1,2,3));
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory productCategory = new ProductCategory("今日特价",5);
        ProductCategory result = ps.save(productCategory);
        Assert.assertNotNull(result);
    }
}