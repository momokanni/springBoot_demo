package com.txhl.wxorder.dao;

import com.txhl.wxorder.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单详细
 * class_name: OrderDetailDao
 * package: com.txhl.wxorder.dao
 * creat_date: 2018/4/2
 * creat_time: 11:22
 **/
public interface OrderDetailDao extends JpaRepository<OrderDetail,String>{

    List<OrderDetail> findByOrderId(String orderId);
}
