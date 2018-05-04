package com.txhl.wxorder.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品
 * @author Administrator
 * @create 2018-04-01 20:41
 */
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = -5036293249726956818L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
