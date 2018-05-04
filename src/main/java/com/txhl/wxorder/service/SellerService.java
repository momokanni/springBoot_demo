package com.txhl.wxorder.service;

import com.txhl.wxorder.entity.SellerInfo;

/**
 * class_name: SellerService
 * package: com.txhl.wxorder.service
 * describe: 卖家端
 * creat_user: sl
 * creat_date: 2018/4/27
 * creat_time: 11:18
 **/
public interface SellerService {

    /**
     * @param: openid
     * describe: 查询卖家信息
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 11:20
     **/
    SellerInfo findSellerInfoByOpenid(String openid);
}
