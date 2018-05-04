package com.txhl.wxorder.dao;

import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单
 * class_name: OrderMasterDao
 * package: com.txhl.wxorder.dao
 * creat_date: 2018/4/2
 * creat_time: 11:23
 **/
public interface OrderMasterDao extends JpaRepository<OrderMaster,String>{

    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);

    OrderMaster findByBuyerOpenidAndOrderId(String openid,String orderid);
}
