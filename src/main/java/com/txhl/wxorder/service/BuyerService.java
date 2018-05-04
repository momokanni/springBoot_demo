package com.txhl.wxorder.service;

import com.txhl.wxorder.dto.OrderMasterDTO;

/**
 * class_name: BuyerService
 * package: com.txhl.wxorder.service
 * describe: 买家业务
 * creat_user: 孙林
 * creat_date: 2018/4/5
 * creat_time: 20:43
 **/
public interface BuyerService {

    //通过openId,orderId查询单个订单
    OrderMasterDTO cancel(String openId, String orderId);
}
