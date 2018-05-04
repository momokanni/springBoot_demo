package com.txhl.wxorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.txhl.wxorder.enums.ProductInfoEnums;
import com.txhl.wxorder.util.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 *
 * @author Administrator
 * @create 2018-03-29 20:19
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo implements Serializable{

    private static final long serialVersionUID = -956367635283320631L;

    @Id
    private String productId;
    // 名称
    private String productName;
    // 价格
    private BigDecimal productPrice;
    // 库存
    private Integer productStock;
    // 描述
    private String productDescription;
    // 小图
    private String productIcon;
    // 状态
    private Integer productStatus = ProductInfoEnums.SETOFF.getCode();
    // 类目编号
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductInfoEnums getProductInfoEnums(){

        return EnumUtil.getByCode(productStatus,ProductInfoEnums.class);
    }
}
