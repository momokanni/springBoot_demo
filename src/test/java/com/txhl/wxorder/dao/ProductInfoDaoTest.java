package com.txhl.wxorder.dao;

import com.txhl.wxorder.entity.ProductInfo;
import com.txhl.wxorder.enums.ProductInfoEnums;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void addOne(){
        try {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductId("123456");
            productInfo.setProductName("皮蛋粥");
            productInfo.setProductPrice(new BigDecimal(6.8));
            productInfo.setProductStock(200);;
            productInfo.setProductDescription("皮蛋+粥，营养美味");
            productInfo.setProductIcon("https://www.baidu.com");
            productInfo.setProductStatus(ProductInfoEnums.NORMAL.getCode());
            productInfo.setCategoryType(3);
            ProductInfo result = productInfoDao.save(productInfo);
            Assert.assertNotNull(result);
        }catch (Exception e){
            log.error(e.toString());
        }
    }

    @Test
    public void findByProductStatus(){
        List<ProductInfo> list = productInfoDao.findProductByProductStatus(0);
        Assert.assertNotEquals(0,list.size());
    }
}