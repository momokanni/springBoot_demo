package com.txhl.wxorder.service;

import com.txhl.wxorder.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * class_name: OrderMasterService
 * package: com.txhl.wxorder.service
 * describe: 订单业务
 * creat_user: 孙林
 * creat_date: 2018/4/2
 * creat_time: 11:57
 **/
public interface OrderMasterService {

    //创建
    OrderMasterDTO create(OrderMasterDTO orderMaster);
    //查询单个订单
    OrderMasterDTO findOne(String orderId);
    //通过openId,orderId查询单个订单
    OrderMasterDTO findOne(String openId,String orderId);
    //查询列表
    Page<OrderMasterDTO> findList(String buyerOPenid, Pageable pageable);
    //查询列表
    Page<OrderMasterDTO> findList(Pageable pageable);
    //取消
    OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO);
    //完成
    OrderMasterDTO finish(OrderMasterDTO orderMasterDTO);
    //支付
    OrderMasterDTO paid(OrderMasterDTO orderMasterDTO);
}
