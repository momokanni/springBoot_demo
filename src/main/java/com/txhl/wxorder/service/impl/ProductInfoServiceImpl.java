package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.dao.ProductInfoDao;
import com.txhl.wxorder.dto.CarDTO;
import com.txhl.wxorder.entity.ProductInfo;
import com.txhl.wxorder.enums.ProductInfoEnums;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品业务层
 *
 * @author Administrator
 * @create 2018-04-01 19:59
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "productInfo")
public class ProductInfoServiceImpl implements ProductInfoService{

    @Autowired
    private ProductInfoDao productInfoDao;

    /**
     * 单个商品
     * param:
     * describe: TODO
     * creat_user: TODO
     * creat_date: 2018/4/4
     * creat_time: 14:21
     **/
    @Cacheable(key = "#productId",condition = "#productId.length() > 3")
    @Override
    public ProductInfo findOne(String productId) {

        return productInfoDao.findById(productId).get();
    }

    /**
     * 全部商品
     * param:
     * describe: TODO
     * creat_user: TODO
     * creat_date: 2018/4/4
     * creat_time: 14:21
     **/
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {

        return productInfoDao.findAll(pageable );
    }

    /**
     * @param: productId
     * describe: 上架
     * creat_user: sl
     * creat_date: 2018/4/25
     * creat_time: 21:45
     **/
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoDao.findById(productId).get();
        if(productInfo == null){
            log.error("【商品上架】 商品不存在 productId = {}",productId);
            throw new WXOrderException(ResultEnums.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus().equals(ProductInfoEnums.NORMAL.getCode())){
            log.error("【商品上架】 商品状态错误 productId = {},prodStatus = {}",productId,productInfo.getProductStatus());
            throw new WXOrderException(ResultEnums.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductInfoEnums.NORMAL.getCode());
        return productInfoDao.save(productInfo);
    }

    /**
     * @param: productId
     * describe: 下架
     * creat_user: sl
     * creat_date: 2018/4/25
     * creat_time: 21:45
     **/
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoDao.findById(productId).get();
        if(productInfo == null){
            log.error("【商品上架】 商品不存在 productId = {}",productId);
            throw new WXOrderException(ResultEnums.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus().equals(ProductInfoEnums.SETOFF.getCode())){
            log.error("【商品上架】 商品状态错误 productId = {},prodStatus = {}",productId,productInfo.getProductStatus());
            throw new WXOrderException(ResultEnums.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductInfoEnums.SETOFF.getCode());
        return productInfoDao.save(productInfo);
    }

    /**
     * 上架商品
     * param:
     * describe: TODO
     * creat_user: TODO
     * creat_date: 2018/4/4
     * creat_time: 14:20
     **/
    @Override
    public List<ProductInfo> findOnLine() {
        return productInfoDao.findProductByProductStatus(ProductInfoEnums.NORMAL.getCode());
    }

    /**
     * 添加产品
     * param:
     * describe: TODO
     * creat_user: TODO
     * creat_date: 2018/4/4
     * creat_time: 14:20
     **/
    @CachePut(key = "#productInfo.productId",condition = "#productInfo.productId.length() > 3")
    @Override
    public ProductInfo save(ProductInfo productInfo) {

        return productInfoDao.save(productInfo);
    }

    /**
     * param:
     * describe: 加库存
     * creat_user: 孙林
     * creat_date: 2018/4/4
     * creat_time: 14:20
     **/
    @Override
    @Transactional
    public void increaseStock(List<CarDTO> carList) {
        for (CarDTO carDTO:carList) {
            ProductInfo productInfo = productInfoDao.findById(carDTO.getProductId()).get();
            if(productInfo == null){
                throw new WXOrderException(ResultEnums.PRODUCT_NOT_EXIST);
            }
            int reslut = productInfo.getProductStock() + carDTO.getProductQuantity();
            productInfo.setProductStock(reslut);
            productInfoDao.save(productInfo);
        }
    }

    /**
     * param:
     * describe: 减库存
     * creat_user: 孙林
     * creat_date: 2018/4/4
     * creat_time: 14:20
     **/
    @Override
    @Transactional
    public void reduceStock(List<CarDTO> carList) {
        for (CarDTO carDTO:carList) {
            ProductInfo productInfo = productInfoDao.getOne(carDTO.getProductId());
            if(productInfo == null){
                throw new WXOrderException(ResultEnums.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - carDTO.getProductQuantity();
            if(result < 0 ){
                throw new WXOrderException(ResultEnums.PRODUCT_LACK_STOCK);
            }
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }
    }
}
