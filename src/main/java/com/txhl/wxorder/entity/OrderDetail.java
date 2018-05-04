package com.txhl.wxorder.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 订单详细
 *
 * @author Administrator
 * @create 2018-04-02 10:34
 */
@Entity
@DynamicUpdate
@Data
public class OrderDetail {

    @Id
    private String detailId;
    //订单ID
    private String orderId;
    //商品ID
    private String productId;
    //商品名称
    private String productName;
    //商品单价
    private BigDecimal productPrice;
    //购买数量
    private Integer productQuantity;
    //商品小图
    private String productIcon;


}
