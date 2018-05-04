package com.txhl.wxorder.constants;

/**
 * 常量维护类
 *
 * @author Administrator
 * @create 2018-04-26 10:32
 */
public interface Constants {
    //后台管理 --> 订单管理 跳转路径
    String ORDER_REDIRECT_URL = "/wxorder/seller/order/list";

    //后台管理 --> 商品管理 跳转路径
    String PRODUCT_REDIRECT_URI = "/wxorder/seller/product/list";

    //后台管理 --> 类目管理 跳转路径
    /*类目详情*/
    String CATEGORY_REDIRECT_URI = "/wxorder//seller/category/categoryDetail";
    /*类目列表*/
    String CATEGORY_LIST_REDIRECT_URI = "/wxorder//seller/category/list";

    //错误页面
    String MODELVIEW_ERROR = "common/error";
    //成功页面
    String MODELVIEW_SUCCESS = "common/success";
}
