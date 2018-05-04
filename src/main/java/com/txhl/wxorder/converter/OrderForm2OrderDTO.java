package com.txhl.wxorder.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.entity.OrderDetail;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证表单转OrderMasterDTO
 *
 * @author Administrator
 * @create 2018-04-04 19:41
 */
@Slf4j
public class OrderForm2OrderDTO {

    public static OrderMasterDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();

        orderMasterDTO.setBuyerName(orderForm.getName());
        orderMasterDTO.setBuyerAddress(orderForm.getAddress());
        orderMasterDTO.setBuyerPhone(orderForm.getPhone());
        orderMasterDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());

        }catch (Exception e){
            log.error("【对象转换】错误,String={}",orderForm.getItems());
            throw new WXOrderException(ResultEnums.PARAM_CONVERT_ERROR);
        }
        orderMasterDTO.setOrderDetailList(orderDetailList);


        return orderMasterDTO;
    }
}
