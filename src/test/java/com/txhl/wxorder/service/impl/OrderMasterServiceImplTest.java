package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.entity.OrderDetail;
import com.txhl.wxorder.enums.OrderStatusEnums;
import com.txhl.wxorder.enums.PayStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    private final String ORDERID = "1522760125965216162";

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    @Test
    public void create() {

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyerName("孙林");
        orderMasterDTO.setBuyerAddress("西直门北大街32号枫蓝国际1106");
        orderMasterDTO.setBuyerPhone("18615204581");
        orderMasterDTO.setBuyerOpenid("aofjsfmdlsoisdifws34xb");

        List<OrderDetail> orderList = new ArrayList<OrderDetail>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(2);

        orderList.add(orderDetail);

        orderMasterDTO.setOrderDetailList(orderList);

        OrderMasterDTO result = orderMasterService.create(orderMasterDTO);

        Assert.assertNotNull(result);
    }

    @Test
    public void findOne(){
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(ORDERID);
        Assert.assertNotEquals("ss",orderMasterDTO.getBuyerName());
    }

    @Test
    public void findList(){
        Page<OrderMasterDTO> orderMasterDTOPage =
                orderMasterService.findList(ORDERID,
                                            PageRequest.of(1,5));
        Assert.assertNotEquals(0,orderMasterDTOPage.getTotalElements());
    }

    @Test
    public void findAllList(){
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findList(PageRequest.of(1,10));

        //Assert.assertNotEquals(0,orderMasterDTOPage.getTotalElements());
        Assert.assertTrue("查询所有订单列表",orderMasterDTOPage.getTotalElements() > 0 );
    }

    @Test
    public void cancel(){
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(ORDERID);
        OrderMasterDTO result = orderMasterService.cancel(orderMasterDTO);
        Assert.assertNotEquals(OrderStatusEnums.CANCEL.getCode(),orderMasterDTO.getOrderStatus());
    }

    @Test
    public void finish(){
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(ORDERID);
        OrderMasterDTO result = orderMasterService.finish(orderMasterDTO);
        Assert.assertNotEquals(OrderStatusEnums.NEW.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid(){
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(ORDERID);
        OrderMasterDTO result = orderMasterService.paid(orderMasterDTO);
        Assert.assertNotEquals(PayStatusEnums.WAIT.getCode(),result.getPayStatus());
    }
}