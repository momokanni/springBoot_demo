package com.txhl.wxorder.enums;

import lombok.Getter;

/**
 * class_name: ResultEnums
 * package: com.txhl.wxorder.enums
 * describe: 返回结果
 * creat_user: 孙林
 * creat_date: 2018/4/4
 * creat_time: 14:48
 **/
@Getter
public enum ResultEnums {
    /**
     * system errors
     */
    SUCCESS(200,"成功"),
    FAILED(201,"失败"),
    UNKONW(202,"未知错误"),
    PARAM_ERROR(203,"参数不正确"),
    PARAM_CONVERT_ERROR(204,"参数转换异常"),

    /**
     * 登录 登出
     */
    LOGIN_OUT_SUCCESS(231,"登出成功"),
    LOGIN_OUT_ERROR(232,"登出失败"),

    /**
     * 商家 seller
     */
    SELLER_NOT_EXISTS(235,"商家不存在"),

    /**
     * 活动
     */
    ACTIVE_NOT_EXISTS(290,"活动不存在"),
    ACTIVE_FINISHED(291,"活动已结束"),
    ACTIVE_MORE_POPULAR(292,"人从众，稍后再试"),

    /**
     * 商品 product
     */
    PRODUCT_NOT_EXIST(301,"商品不存在"),
    PRODUCT_LACK_STOCK(302,"商品库存不足"),
    PRODUCT_STATUS_ERROR(303,"商品状态异常"),
    PRODUCT_STATUS_ONSALE(304,"上架成功"),
    PRODUCT_STATUS_OFFSALE(305,"下架成功"),
    PRODUCT_UPDATE_SUCCESS(306,"商品修改成功"),

    /**
     * 购物车
     */
    SHOPCAR_NOT_EMPTY(351,"购物车不能为空"),

    /**
     * 主订单 order master
     */
    ORDER_NOT_EXISTS(401,"订单不存在"),
    ORDER_STATUS_ERROR(402,"订单状态不正确"),
    ORDER_UPDATE_ERROR(403,"订单更新失败"),
    ORDER_DETAIL_EMPTY(405,"订单详情为空"),
    ORDER_PAY_STATUS_EROR(406,"订单支付状态不正确"),
    ORDER_UPDATE_PAY_EROR(407,"订单支付更新失败"),
    ORDER_OPENID_ERROR(408,"此订单不属于当前用户"),
    ORDER_CANCEL_SUCCESS(409,"订单取消成功"),
    ORDER_FINISHED_SUCCESS(410,"订单已完成"),

    /**
     * 订单明细 order detail
     */
    ORDER_DETAIL_NOT_EXISTS(451,"订单详细不存在"),

    /**
     * 微信公众号（MP）WeChat exception
     */
    WECHAT_MP_OAUTH(501,"微信公众平台授权异常"),
    WECHAT_MENU_SUCCESS(502,"公众号菜单设置成功"),
    WECHAT_MENU_ERROR(503,"公众号菜单设置失败"),

    /**
     * 微信开放平台（OPEN）
     */
    WECHAT_OPEN_QRAUTH(536,"微信开放平台授权异常"),

    /**
     * 微信支付 WeChat_Pay exception
     */
    WXPAY_ERROR(551,"支付失败"),
    WXPAY_CANCEL(552,"取消支付"),
    WXPAY_NOTIFY_AMOUNT_ERROR(553,"微信支付异步通知金额校验不通过")

    ;

    private Integer code;

    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
