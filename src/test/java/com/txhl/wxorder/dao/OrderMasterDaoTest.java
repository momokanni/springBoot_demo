package com.txhl.wxorder.dao;

import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.entity.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    public void findByBuyerOpenid() {
        Page<OrderMaster> result = orderMasterDao.findByBuyerOpenid("123456",PageRequest.of(1,10));
        Assert.assertNotEquals(0,result.getTotalElements());
    }
}