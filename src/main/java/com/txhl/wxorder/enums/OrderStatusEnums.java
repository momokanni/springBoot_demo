package com.txhl.wxorder.enums;

import lombok.Getter;

/**
 * class_name: OrderStatusEnums
 * package: com.txhl.wxorder.enums
 * describe: 订单状态
 * creat_user: 孙林
 * creat_date: 2018/4/2
 * creat_time: 10:48
 **/
@Getter
public enum OrderStatusEnums implements CodeEnums {
    NEW(0,"新订单"),
    FINISHED(1,"完成"),
    CANCEL(2,"已取消"),
    SENDING(3,"配送中");

    private Integer code;

    private String msg;

    OrderStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
