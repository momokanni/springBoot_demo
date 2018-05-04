package com.txhl.wxorder.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.txhl.wxorder.entity.ProductInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品（包含类目）
 * @author Administrator
 * @create 2018-04-01 20:36
 */
@Data
public class CategoryAndProductVO implements Serializable {

    private static final long serialVersionUID = 5446314382964906360L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
