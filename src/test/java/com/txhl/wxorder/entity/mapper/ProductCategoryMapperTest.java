package com.txhl.wxorder.entity.mapper;

import com.txhl.wxorder.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name", "夏日必选");
        map.put("category_type", "5");
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1, result);
    }

    @Test
    public void insertByObject() {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("春末热卖");
        category.setCategoryType(6);
        int result = mapper.insertByObject(category);
        Assert.assertEquals(1, result);
    }

    @Test
    public void findByCategoryType() {
        ProductCategory category = mapper.findByCategoryType(5);
        Assert.assertNotNull(category);
    }

    @Test
    public void findByCategoryName() {
        List<ProductCategory> category = mapper.findByCategoryName("夏日必选");
        Assert.assertEquals(1, category.size());
    }

    @Test
    public void updateByCategoryType() {
        int result = mapper.updateByCategoryType("夏日优选", 5);
        Assert.assertEquals(1, result);
    }

    @Test
    public void updateByProductCategory() {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("夏日惠选");
        category.setCategoryType(5);
        int result = mapper.updateByObject(category);
        Assert.assertEquals(1, result);
    }

    @Test
    public void deleteByCategoryType() {
        int result = mapper.deleteByCategoryType(6);
        Assert.assertEquals(1, result);
    }
}