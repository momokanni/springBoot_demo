package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.dao.SellerInfoDao;
import com.txhl.wxorder.entity.SellerInfo;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 卖家端
 *
 * @author Administrator
 * @create 2018-04-27 11:31
 */
@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    /**
     * @param: openid
     * describe: 查询卖家信息
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 12:09
     **/
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {

        if (openid == null){
           log.error("【卖家信息】 openid不能为空 openid = {}",openid);
           throw new WXOrderException(ResultEnums.PARAM_ERROR);
        }

        SellerInfo sellerInfo = sellerInfoDao.findByOpenid(openid);
        if (sellerInfo == null){
            log.error("【卖家信息】 商家不存在 openid = {}",openid);
            throw new WXOrderException(ResultEnums.SELLER_NOT_EXISTS);
        }

        return sellerInfo;
    }
}
