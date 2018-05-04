package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.entity.ProductInfo;
import com.txhl.wxorder.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

/**
 * 商品测试
 * class_name: ProductInfoServiceImplTest
 * package: com.txhl.wxorder.service.impl
 * creat_date: 2018/4/1
 * creat_time: 20:08
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findAll() {
        Page<ProductInfo> ps = productInfoService.findAll(PageRequest.of(1,10));
        log.info("元素总数：{}",ps.getTotalElements());
        Assert.assertNotEquals(0,ps.getTotalElements());
    }

    @Test
    public void findOnLine() {
        List<ProductInfo> list = productInfoService.findOnLine();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() {

    }

    @Test
    public void onSale() {
        ProductInfo productInfo = productInfoService.onSale("123456");
        Assert.assertNotEquals(0,productInfo.getProductStatus().toString());
    }

    @Test
    public void setOff() {
        ProductInfo productInfo = productInfoService.offSale("123456");
        Assert.assertNotEquals(0,productInfo.getProductStatus().toString());
    }
}