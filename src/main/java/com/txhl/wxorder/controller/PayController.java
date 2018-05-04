package com.txhl.wxorder.controller;

import com.lly835.bestpay.model.PayResponse;
import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.OrderMasterService;
import com.txhl.wxorder.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 支付
 *
 * @author Administrator
 * @create 2018-04-17 11:01
 */
@Controller
@RequestMapping(value = "pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private PayService payService;

    /**
     * @param:
     * describe: 创建支付订单
     * creat_user:
     * creat_date: 2018/4/24
     * creat_time: 16:33
     **/
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        //1、查询订单
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        if (orderMasterDTO == null) {
            log.error("【微信支付】 订单不存在 orderId={}",orderId);
            throw new WXOrderException(ResultEnums.ORDER_NOT_EXISTS);
        }

        //2、发起支付
        PayResponse payResponse = payService.create(orderMasterDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create",map);
    }

    /**
     * @param:
     * describe: 支付异步通知
     * creat_user:
     * creat_date: 2018/4/24
     * creat_time: 16:33
     **/
    @PostMapping(value = "notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }


}
