package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.entity.OrderMaster;
import com.txhl.wxorder.enums.OrderStatusEnums;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.BuyerService;
import com.txhl.wxorder.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 买家业务处理
 *
 * @author Administrator
 * @create 2018-04-05 21:09
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public OrderMasterDTO cancel(String openId, String orderId) {
        OrderMasterDTO orderMasterDTO = checkOwner(openId,orderId);
        if (orderMasterDTO == null){
            log.error("【取消订单】 查询不到该订单 orderId={}",orderId);
            throw new WXOrderException(ResultEnums.ORDER_NOT_EXISTS);
        }
        if(orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.CANCEL.getCode())||
                orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.FINISHED.getCode())){
            log.error("【取消订单】 订单状态不正确，orderId={},orderStatus={}",orderId,orderMasterDTO.getOrderStatus());
            throw new WXOrderException(ResultEnums.ORDER_STATUS_ERROR);
        }
        return orderMasterService.cancel(orderMasterDTO);
    }

    private OrderMasterDTO checkOwner(String openId, String orderId){
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        if(orderMasterDTO == null){
            return null;
        }
        if(!orderMasterDTO.getBuyerOpenid().equalsIgnoreCase(openId)){
            log.error("【查询订单】 订单owner不一致，openId={},orderId={}",openId,orderId);
            throw new WXOrderException(ResultEnums.ORDER_OPENID_ERROR);
        }

        return orderMasterDTO;
    }
}
