package com.txhl.wxorder.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.enums.PayStatusEnums;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.OrderMasterService;
import com.txhl.wxorder.service.PayService;
import com.txhl.wxorder.util.JsonUtil;
import com.txhl.wxorder.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 支付业务层
 *
 * @author Administrator
 * @create 2018-04-17 11:09
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private  static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * @param: orderMasterDTO
     * describe: 生成支付订单
     * creat_user:
     * creat_date: 2018/4/24
     * creat_time: 13:53
     **/
    @Override
    public PayResponse create(OrderMasterDTO orderMasterDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderMasterDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderMasterDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】 发起支付 request = {}", JsonUtil.toJson(payRequest.toString()));
        PayResponse payResponse = null;
        try {
            payResponse = bestPayService.pay(payRequest);
        } catch (RuntimeException e){
            log.error("【微信支付】 发起支付 失败原因 = {}",e.getMessage());
            throw new WXOrderException(ResultEnums.WXPAY_ERROR.getCode(),e.getMessage());
        }
        log.info("【微信支付】 发起支付 response = {}",JsonUtil.toJson(payResponse.toString()));
        return payResponse;
    }

    /**
     * @param: notify
     * describe: 支付消息异步通知
     * creat_user:
     * creat_date: 2018/4/24
     * creat_time: 13:54
     **/
    @Override
    public PayResponse notify(String notify) {
        //安全验证
        //1、验证签名
        //2、支付状态
        //3、支付金额
        //4、支付人（下单人 == 支付人）
        PayResponse payResponse = bestPayService.asyncNotify(notify);
        log.info("【微信支付】 异步通知，payResponse = {}",JsonUtil.toJson(payResponse.toString()));

        //查询订单
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if(orderMasterDTO == null){
            log.error("【微信支付】 异步通知，订单不存在，orderId = {}",payResponse.getOrderId());
            throw new WXOrderException(ResultEnums.ORDER_NOT_EXISTS);
        }

        //判断金额是否一致(0.10,0.1)
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderMasterDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】 异步通知，订单金额不一致，orderId = {},微信通知金额 = {}，系统金额 = {}",
                      payResponse.getOrderId(),
                      payResponse.getOrderAmount(),
                      orderMasterDTO.getOrderAmount());
            throw new WXOrderException(ResultEnums.WXPAY_NOTIFY_AMOUNT_ERROR);
        }
        //修改订单的支付状态
        orderMasterService.paid(orderMasterDTO);
        return payResponse;
    }

    /**
     * @param:
     * describe: 退款
     * creat_user:
     * creat_date: 2018/4/24
     * creat_time: 16:55
     **/
    @Override
    public RefundResponse refund(OrderMasterDTO orderMasterDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderMasterDTO.getOrderId());
        refundRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        RefundResponse result = bestPayService.refund(refundRequest);
        return  result;
    }
}
