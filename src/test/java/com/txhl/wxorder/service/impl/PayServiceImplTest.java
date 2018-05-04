package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.service.OrderMasterService;
import com.txhl.wxorder.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderMasterService orderMasterService;

    @Test
    public void create() {
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne("1522760125965216162");

        payService.create(orderMasterDTO);

    }
}