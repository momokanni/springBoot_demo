package com.txhl.wxorder.controller;

import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.SecKillService;
import com.txhl.wxorder.service.impl.SecKillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀
 *
 * @author Administrator
 * @create 2018-05-02 16:15
 */
@RestController
@Slf4j
@RequestMapping("/kill")
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    /**
     * @param: productId
     * describe: 查询秒杀商品
     * creat_user: sl
     * creat_date: 2018/5/2
     * creat_time: 16:21
     **/
    @GetMapping("query/{productId}")
    public String query(@PathVariable String productId) throws WXOrderException{
        return secKillService.querySecKillProductInfo(productId);
    }

    /**
     * @param: productId
     * describe: 秒杀成功，返回剩余库存，没有抢到，返回提示语"手速慢了..."
     * creat_user: sl
     * creat_date: 2018/5/2
     * creat_time: 16:22
     **/
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception{
        secKillService.orderProductMockDiffUser(productId);
        return secKillService.querySecKillProductInfo(productId);
    }
}
