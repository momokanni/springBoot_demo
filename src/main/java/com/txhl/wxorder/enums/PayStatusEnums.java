package com.txhl.wxorder.enums;

import lombok.Getter;

/**
 * class_name: PayStatusEnums
 * package: com.txhl.wxorder.enums
 * describe: 支付状态
 * creat_user: 孙林
 * creat_date: 2018/4/4
 * creat_time: 14:47
 **/
@Getter
public enum PayStatusEnums implements  CodeEnums {

    WAIT(0,"待支付"),
    SUCCESS(1,"支付成功"),
    FAILED(2,"支付失败"),
    RETURN(3,"待退款"),
    RETURNING(4,"退款中"),
    RETURN_SUCCESS(5,"退款成功"),
    RETURN_FAILED(6,"退款失败");

    PayStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;
}
