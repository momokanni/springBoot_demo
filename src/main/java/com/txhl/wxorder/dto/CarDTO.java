package com.txhl.wxorder.dto;

import lombok.Data;

/**
 * 购物车
 *
 * @author Administrator
 * @create 2018-04-03 16:55
 */
@Data
public class CarDTO {

    //商品ID
    private String productId;
    //购买数量
    private Integer productQuantity;

    public CarDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
