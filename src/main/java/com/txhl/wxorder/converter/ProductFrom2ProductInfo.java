package com.txhl.wxorder.converter;

import com.txhl.wxorder.entity.ProductInfo;
import com.txhl.wxorder.form.ProductForm;
import org.springframework.beans.BeanUtils;

/**
 * 商品表单转换类
 *
 * @author Administrator
 * @create 2018-04-26 14:57
 */
public class ProductFrom2ProductInfo {

    public static ProductInfo convert(ProductForm productForm){
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productForm,productInfo);
        return productInfo;
    };

    public static ProductInfo convert(ProductForm productForm,ProductInfo productInfo){
        BeanUtils.copyProperties(productForm,productInfo);
        return productInfo;
    };

}
