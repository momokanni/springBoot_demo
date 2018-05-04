package com.txhl.wxorder.service;

import com.txhl.wxorder.dto.CarDTO;
import com.txhl.wxorder.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * class_name: ProductInfoService
 * package: com.txhl.wxorder.service
 * describe: 商品业务
 * creat_user: 孙林
 * creat_date: 2018/4/1
 * creat_time: 19:57
 **/
public interface ProductInfoService {

    /**
     * param:
     * describe: 查找单品
     * creat_user: 孙林
     * creat_date: 2018/4/1
     * creat_time: 19:58
     **/
    ProductInfo findOne(String productId);

    /**
     * param:
     * describe: 查找全部
     * creat_user: 孙林
     * creat_date: 2018/4/1
     * creat_time: 19:58
     **/
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * param:
     * describe: 全部上架商品
     * creat_user: 孙林
     * creat_date: 2018/4/1
     * creat_time: 20:05
     **/
    List<ProductInfo> findOnLine();

    /**
     * param:
     * describe: 添加商品
     * creat_user: 孙林
     * creat_date: 2018/4/1
     * creat_time: 20:04
     **/
    ProductInfo save(ProductInfo productInfo);

    //加库存
     void increaseStock(List<CarDTO> carList);

    //减库存
    void reduceStock(List<CarDTO> carList);

    //商品上架/下架
    ProductInfo onSale(String productId);

    ProductInfo offSale(String productId);
}
