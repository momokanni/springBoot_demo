package com.txhl.wxorder.converter;

import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单转换成DTO
 *
 * @author Administrator
 * @create 2018-04-03 21:22
 */
public class OrderMaster2OrderMasterDTO {

    /**
     * 单对象转换
     * param:
     * creat_date: 2018/4/3
     * creat_time: 21:26
     **/
    public static OrderMasterDTO convert(OrderMaster orderMaster){
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster,orderMasterDTO);
        return  orderMasterDTO;
    }

    /**
     * 集合对象转换
     * param:
     * describe:
     * creat_user:
     * creat_date: 2018/4/4
     * creat_time: 14:45
     **/
    public static List<OrderMasterDTO> convert(List<OrderMaster> orderMasters){
        return orderMasters.stream().map(order -> convert(order)).collect(Collectors.toList());
    }
}
