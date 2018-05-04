package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.SecKillService;
import com.txhl.wxorder.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 秒杀业务层
 *
 * @author Administrator
 * @create 2018-05-02 16:16
 */
@Service
@Slf4j
public class SecKillServiceImpl implements SecKillService {

    /**
     * 超时时间 10秒
     */
    private static final int TIMEOUT = 10 * 1000;

    @Autowired
    private RedisLock redisLock;

    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;
    static {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",10000);
        stock.put("123456",10000);
    }

    private String queryMap(String productId){
        return "五一假期，皮蛋粥特价，限量销售"+products.get(productId)+"份，还剩："
                + stock.get(productId)+ "份，该商品成功下单用户数目："+orders.size()+"人";
    }

    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId) {

        //加锁
        long time = System.currentTimeMillis()+ TIMEOUT;
        boolean status = (redisLock.lock(productId,String.valueOf(time)));
        log.info("【redis分布式锁】 加锁时间：{},加锁状态：{}",time,status);
        if(!status){
            throw new WXOrderException(ResultEnums.ACTIVE_MORE_POPULAR);
        }
        //1、查询库存
        int stockNum = stock.get(productId);
        log.info("【redis分布式锁】 库存：{}，{}",stockNum,time);
        if (stockNum == 0){
            throw new WXOrderException(ResultEnums.ACTIVE_FINISHED);
        } else {
            //2、下单
            orders.put(KeyUtil.genUniqueKey(),productId);
            //3、减库存
            stockNum = stockNum -1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }

        //解锁
        redisLock.unlock(productId,String.valueOf(time));
    }
}
