package com.txhl.wxorder.entity;

import com.txhl.wxorder.enums.OrderStatusEnums;
import com.txhl.wxorder.enums.PayStatusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author Administrator
 * @create 2018-04-02 10:29
 */
@Entity
@DynamicUpdate
@Data
public class OrderMaster {

    @Id
    private String orderId;
    //买家名称
    private String buyerName;
    //买家手机号
    private String buyerPhone;
    //配送地址
    private String buyerAddress;
    //买家openid
    private String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;
    //订单状态
    private Integer orderStatus = OrderStatusEnums.NEW.getCode();
    //支付状态
    private Integer payStatus = PayStatusEnums.WAIT.getCode();
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}
