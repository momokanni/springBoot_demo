package com.txhl.wxorder.controller;

import com.txhl.wxorder.constants.Constants;
import com.txhl.wxorder.converter.ProductFrom2ProductInfo;
import com.txhl.wxorder.entity.ProductCategory;
import com.txhl.wxorder.entity.ProductInfo;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.form.ProductForm;
import com.txhl.wxorder.service.ProductCategoryService;
import com.txhl.wxorder.service.ProductInfoService;
import com.txhl.wxorder.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 * @author Administrator
 * @create 2018-04-25 20:13
 */
@Controller
@RequestMapping(value = "/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * @param: page,size
     * describe: 商品列表
     * creat_user: sl
     * creat_date: 2018/4/25
     * creat_time: 21:42
     **/
    @GetMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        Page<ProductInfo> productInfoPage = productInfoService.findAll(PageRequest.of(page-1,size));
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);

        return new ModelAndView("/product/list",map);
    }

    /**
     * @param: productId
     * describe: 上架
     * creat_user: sl
     * creat_date: 2018/4/26
     * creat_time: 11:01
     **/
    @GetMapping(value = "/onSale")
    public ModelAndView onSale(@RequestParam(value = "productId") String productId,
                               Map<String,Object> map){
        try {
            productInfoService.onSale(productId);
        } catch (WXOrderException e) {
            log.error("【商品上架】 发生异常 {}",e);
            map.put("msg",e.getMessage());
            map.put("url", Constants.PRODUCT_REDIRECT_URI);
            return new ModelAndView(Constants.MODELVIEW_ERROR,map);
        }
        map.put("msg", ResultEnums.PRODUCT_STATUS_ONSALE.getMsg());
        map.put("url",Constants.PRODUCT_REDIRECT_URI);
        return new ModelAndView(Constants.MODELVIEW_SUCCESS,map);

    }

    /**
     * @param: productId
     * describe: 下架
     * creat_user: sl
     * creat_date: 2018/4/26
     * creat_time: 11:03
     **/
    @GetMapping(value = "offSale")
    public ModelAndView offSale(@RequestParam(value = "productId") String productId,
                                Map<String,Object> map){
        try {
            productInfoService.offSale(productId);
        } catch (WXOrderException e) {
            log.error("【商品下架】 发生异常 {}",e);
            map.put("msg",e.getMessage());
            map.put("url", Constants.PRODUCT_REDIRECT_URI);
            return new ModelAndView(Constants.MODELVIEW_ERROR,map);
        }
        map.put("msg", ResultEnums.PRODUCT_STATUS_OFFSALE.getMsg());
        map.put("url",Constants.PRODUCT_REDIRECT_URI);
        return new ModelAndView(Constants.MODELVIEW_SUCCESS,map);
    }

    /**
     * @param: productId
     * describe: 商品详情页（修改 + 添加）
     * creat_user: sl
     * creat_date: 2018/4/26
     * creat_time: 14:02
     **/
    @GetMapping(value = "detailView")
    public ModelAndView detailView(@RequestParam(value = "productId",required = false) String productId,
                                   Map<String,Object> map){
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo",productInfo);
        }

        List<ProductCategory> productCategories = productCategoryService.findALL();
        map.put("categoryList",productCategories);

        return new ModelAndView("/product/index",map);
    }

    /**
     * @param: product
     * describe: 新增 or 更新
     * creat_user: sl
     * creat_date: 2018/4/26
     * creat_time: 14:27
     **/
    @PostMapping(value = "save")
    //@CachePut(cacheNames = "product",key = "123")
    //@CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            log.error("【商品详情】 异常原因: {}",bindingResult.getFieldError().getDefaultMessage());
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url", Constants.PRODUCT_REDIRECT_URI);
            return new ModelAndView(Constants.MODELVIEW_ERROR,map);
        }

        ProductInfo productInfo = null;
        try {
            if(!StringUtils.isEmpty(productForm.getProductId())){
                productInfo = productInfoService.findOne(productForm.getProductId());
                productInfo = ProductFrom2ProductInfo.convert(productForm,productInfo);
            } else {
                productInfo = ProductFrom2ProductInfo.convert(productForm);
                productInfo.setProductId(KeyUtil.genUniqueKey());
            }
            productInfoService.save(productInfo);
        } catch (Exception e){
            log.error("【商品详情】 参数异常 {}",productInfo);
            map.put("msg",ResultEnums.PARAM_ERROR.getMsg());
            map.put("url",Constants.PRODUCT_REDIRECT_URI);
            return new ModelAndView(Constants.MODELVIEW_ERROR);
        }

        map.put("msg",ResultEnums.PRODUCT_UPDATE_SUCCESS.getMsg());
        map.put("url",Constants.PRODUCT_REDIRECT_URI);
        return new ModelAndView(Constants.MODELVIEW_SUCCESS,map);
    }
}
