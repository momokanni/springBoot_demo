package com.txhl.wxorder.service;

import com.txhl.wxorder.dto.OrderMasterDTO;

/**
 * class_name: PushMessageService
 * package: com.txhl.wxorder.service
 * describe: 消息推送
 * creat_user: sl
 * creat_date: 2018/4/27
 * creat_time: 21:35
 **/
public interface PushMessageService {

    void OrderStatus(OrderMasterDTO orderMasterDTO);;
}
