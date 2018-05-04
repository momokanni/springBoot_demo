package com.txhl.wxorder.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 卖家信息
 *
 * @author Administrator
 * @create 2018-04-27 10:59
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Date createTime;

    private Date updateTime;
}
