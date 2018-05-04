package com.txhl.wxorder.controller;

import com.txhl.wxorder.converter.OrderForm2OrderDTO;
import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.form.OrderForm;
import com.txhl.wxorder.service.BuyerService;
import com.txhl.wxorder.service.OrderMasterService;
import com.txhl.wxorder.util.ResultUtils;
import com.txhl.wxorder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单
 *
 * @author Administrator
 * @create 2018-04-04 17:36
 */
@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;


    //创建订单
    @RequestMapping(value = "/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确 orderForm={}",orderForm);
            throw new WXOrderException(ResultEnums.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderMasterDTO orderMasterDTO = OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw new WXOrderException(ResultEnums.SHOPCAR_NOT_EMPTY);
        }
        OrderMasterDTO createResult = orderMasterService.create(orderMasterDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultUtils.success(map);
    }


    //订单列表
    @GetMapping(value = "/list")
    private ResultVO<List<OrderMasterDTO>> findOrderList(
                            @RequestParam("openid") String openid,
                            @RequestParam(value = "page",defaultValue = "0") Integer page,
                            @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【订单列表】 openid为空");
            throw new WXOrderException(ResultEnums.PARAM_ERROR);
        }
        Page<OrderMasterDTO> orderMasterDTOResult = orderMasterService.findList(openid,PageRequest.of(page,size));

        return ResultUtils.success(orderMasterDTOResult.getContent());
    }

    //订单详情
    @GetMapping(value = "/detail")
    public ResultVO<OrderMasterDTO> detail(@RequestParam(value = "openId") String openId,
                                           @RequestParam(value = "orderId") String orderId){

        if(StringUtils.isEmpty(openId)){
            log.error("【订单列表】 openid为空");
            throw new WXOrderException(ResultEnums.PARAM_ERROR);
        }

        if(StringUtils.isEmpty(orderId)){
            log.error("【订单列表】 orderId为空");
            throw new WXOrderException(ResultEnums.PARAM_ERROR);
        }

        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(openId,orderId);

        return ResultUtils.success(orderMasterDTO);
    }

    //取消订单
    @PostMapping(value = "cancel")
    public ResultVO cancel(@RequestParam(value = "openId") String openId,
                           @RequestParam(value = "orderId")String orderId){

        buyerService.cancel(openId,orderId);
        return ResultUtils.success();
    }
}
