package com.txhl.wxorder.dao;

import com.txhl.wxorder.entity.SellerInfo;
import com.txhl.wxorder.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoDaoTest {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setId(KeyUtil.genUniqueKey());
        sellerInfo.setOpenid("abc");
        sellerInfo.setUsername("123456");
        sellerInfo.setPassword("123456");

        SellerInfo result = sellerInfoDao.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() {

        SellerInfo sellerInfo = sellerInfoDao.findByOpenid("abc");
        Assert.assertNotEquals("123",sellerInfo.getOpenid());
    }

}