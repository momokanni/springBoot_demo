package com.txhl.wxorder.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.txhl.wxorder.dto.OrderMasterDTO;

/**
 * class_name: PayService
 * package: com.txhl.wxorder.service
 * describe: 支付
 * creat_user:
 * creat_date: 2018/4/17
 * creat_time: 11:07
 **/
public interface PayService {
    PayResponse create(OrderMasterDTO orderMasterDTO);

    PayResponse notify(String notify);

    RefundResponse refund(OrderMasterDTO orderMasterDTO);
}
