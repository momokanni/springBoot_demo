package com.txhl.wxorder.enums;

import lombok.Getter;

/**
 * class_name: ProductInfoEnums
 * package: com.txhl.wxorder.enums
 * describe: 商品状态
 * creat_user: 孙林
 * creat_date: 2018/4/4
 * creat_time: 14:47
 **/
@Getter
public enum ProductInfoEnums implements CodeEnums {
    NORMAL(0,"正常"),SETOFF(1,"下架");

    private Integer code;

    private String msg;

    ProductInfoEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
