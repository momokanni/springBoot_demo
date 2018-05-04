package com.txhl.wxorder.form;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品提交表单
 *
 * @author Administrator
 * @create 2018-04-26 14:40
 */
@Data
public class ProductForm {

    private String productId;
    // 名称
    @NotEmpty(message = "商品名称不能为空")
    private String productName;
    // 商品价格
    @NotNull(message = "商品价格不能为空")
    @DecimalMin(value = "0.00", message = "商品价格不能小于1")
    private BigDecimal productPrice;
    // 商品库存
    @NotNull(message = "商品库存不能为空")
    @Min(value = 0,message = "商品库存不能小于0")
    private Integer productStock;
    // 商品描述
    private String productDescription;
    // 商品图片
    @NotEmpty(message = "商品图片不能为空")
    private String productIcon;
    // 类目编号
    private Integer categoryType;
}
