package com.txhl.wxorder.service.impl;

import com.lly835.bestpay.model.RefundResponse;
import com.txhl.wxorder.converter.OrderMaster2OrderMasterDTO;
import com.txhl.wxorder.dao.OrderDetailDao;
import com.txhl.wxorder.dao.OrderMasterDao;
import com.txhl.wxorder.dto.CarDTO;
import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.entity.OrderDetail;
import com.txhl.wxorder.entity.OrderMaster;
import com.txhl.wxorder.entity.ProductInfo;
import com.txhl.wxorder.enums.OrderStatusEnums;
import com.txhl.wxorder.enums.PayStatusEnums;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.OrderMasterService;
import com.txhl.wxorder.service.PayService;
import com.txhl.wxorder.service.ProductInfoService;
import com.txhl.wxorder.util.JsonUtil;
import com.txhl.wxorder.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单业务层
 * @author Administrator
 * @create 2018-04-02 13:49
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService{

    @Autowired
    private OrderMasterDao omd;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private PayService payService;
    @Autowired
    private WebSocket webSocket;

    /**
     * param:
     * describe: 创建订单
     * creat_user: sl
     * creat_date: 2018/4/4
     * creat_time: 14:49
     **/
    @Override
    @Transactional
    public OrderMasterDTO create(OrderMasterDTO orderMasterDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //查询商品（数量、单价）
        for (OrderDetail od:orderMasterDTO.getOrderDetailList()) {
            ProductInfo pi = productInfoService.findOne(od.getProductId());
            if(pi == null) {
                throw new WXOrderException(ResultEnums.PRODUCT_NOT_EXIST);
            }
            //计算总价
            orderAmount = pi.getProductPrice()
                        .multiply(new BigDecimal(od.getProductQuantity()))
                        .add(orderAmount);
            //写入订单详情库
            od.setOrderId(orderId);
            od.setDetailId(KeyUtil.genUniqueKey());
            BeanUtils.copyProperties(pi,od);
            orderDetailDao.save(od);
        }
        //写入订单主库
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        omd.save(orderMaster);
        //减库存
        List<CarDTO> carList = orderMasterDTO.getOrderDetailList()
                               .stream()
                               .map(e -> new CarDTO(e.getProductId(),e.getProductQuantity()))
                                .collect(Collectors.toList());
        productInfoService.reduceStock(carList);

        //往前台发送消息
        webSocket.sendMessage(orderMasterDTO.getOrderId());
        return orderMasterDTO;
    }

    /**
     * param:
     * describe: 查询单个订单
     * creat_user: 孙林
     * creat_date: 2018/4/3
     * creat_time: 20:33
     **/
    @Override
    public OrderMasterDTO findOne(String orderId) {
        OrderMaster orderMaster = omd.findById(orderId).get();
        if (orderMaster == null){
            throw new WXOrderException(ResultEnums.ORDER_NOT_EXISTS);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new WXOrderException(ResultEnums.ORDER_DETAIL_NOT_EXISTS);
        }
        OrderMasterDTO orderMasterDTO = OrderMaster2OrderMasterDTO.convert(orderMaster);
        orderMasterDTO.setOrderDetailList(orderDetailList);
        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO findOne(String openId, String orderId) {
        OrderMaster orderMaster = omd.findByBuyerOpenidAndOrderId(openId,orderId);
        if (orderMaster == null){
            log.error("【查询订单】 订单不存在");
            throw new WXOrderException(ResultEnums.ORDER_NOT_EXISTS);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            log.error("【查询订单】 该订单详情为空");
            throw new WXOrderException(ResultEnums.ORDER_DETAIL_NOT_EXISTS);
        }
        OrderMasterDTO orderMasterDTO = OrderMaster2OrderMasterDTO.convert(orderMaster);
        orderMasterDTO.setOrderDetailList(orderDetailList);
        return orderMasterDTO;
    }

    /**
     * param: buyerOPenid
     * describe: 查询订单列表
     * creat_user: sl
     * creat_date: 2018/4/4
     * creat_time: 14:49
     **/
    @Override
    public Page<OrderMasterDTO> findList(String buyerOPenid, Pageable pageable) {
        Page<OrderMaster> orderMasterList = omd.findByBuyerOpenid(buyerOPenid,pageable);
        List<OrderMasterDTO> orderMasterDTOPage = OrderMaster2OrderMasterDTO.convert(orderMasterList.getContent());
        Page<OrderMasterDTO> orderMasterDTOPage1 = new PageImpl<OrderMasterDTO>(orderMasterDTOPage,pageable,orderMasterList.getTotalElements());
        return orderMasterDTOPage1;
    }

    /**
     * @param:
     * describe: 查询所有订单列表
     * creat_user: sl
     * creat_date: 2018/4/24
     * creat_time: 18:52
     **/
    @Override
    public Page<OrderMasterDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasters = omd.findAll(pageable);
        List<OrderMasterDTO> orderMasterDTOS = OrderMaster2OrderMasterDTO.convert(orderMasters.getContent());

        return new PageImpl<OrderMasterDTO>(orderMasterDTOS,pageable,orderMasters.getTotalElements());
    }

    /**
     * param:
     * describe: 取消订单
     * creat_user: sl
     * creat_date: 2018/4/4
     * creat_time: 14:50
     **/
    @Override
    @Transactional
    public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);
        //判断订单状态
        if(!orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode()) && !orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.CANCEL.getCode())){
            log.error("【取消订单】 订单状态不正确，orderId={}，orderStatus={}",orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
            throw new WXOrderException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        OrderMaster updateResult = omd.save(orderMaster);
        if(updateResult == null){
            log.error("【取消订单】 更新失败，orderMaster={}",orderMaster);
            throw new WXOrderException(ResultEnums.ORDER_UPDATE_ERROR);
        }
        //返还库存
        //<1>.判断该订单有没有商品
        if(CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())){
            log.error("【取消订单】 订单中无商品详情,orderDTO={}",orderMasterDTO);
            throw new WXOrderException(ResultEnums.ORDER_DETAIL_EMPTY);
        }

        List<CarDTO> carDTOList = orderMasterDTO.getOrderDetailList()
                                 .stream().map(e -> new CarDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(carDTOList);
        //已支付需退款
        if (orderMasterDTO.getOrderStatus().equals(PayStatusEnums.SUCCESS.getCode())){
            RefundResponse refund = payService.refund(orderMasterDTO);
            log.info("【微信退款】 退款详细：{}", JsonUtil.toJson(refund));
        }
        return orderMasterDTO;
    }

    /**
     * param:
     * describe:订单完成
     * creat_user: sl
     * creat_date: 2018/4/4
     * creat_time: 14:50
     **/
    @Override
    @Transactional
    public OrderMasterDTO finish(OrderMasterDTO orderMasterDTO) {
        //判断订单状态
        if(!orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【订单完成】 订单状态不正确 orderId={},orderStatus={}",orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
            throw new WXOrderException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderMasterDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);
        OrderMaster orderMaster1 = omd.save(orderMaster);
        if(orderMaster1 == null){
            log.error("【订单完成】 更新失败，orderMaster={}",orderMaster);
            throw new WXOrderException(ResultEnums.ORDER_UPDATE_ERROR);
        }
        return orderMasterDTO;
    }

    /**
     * param:
     * describe: 订单支付
     * creat_user:
     * creat_date: 2018/4/4
     * creat_time: 14:50
     **/
    @Override
    @Transactional
    public OrderMasterDTO paid(OrderMasterDTO orderMasterDTO) {
        //判断订单状态
        if(!orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【订单支付】 订单状态不正确 orderId={},orderStatus={}",orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
            throw new WXOrderException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderMasterDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("【订单支付】，订单支付状态不正确，orderMasterDTO={}",orderMasterDTO);
            throw new WXOrderException(ResultEnums .ORDER_PAY_STATUS_EROR);
        }
        //修改支付状态
        orderMasterDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);
        OrderMaster orderMaster1 = omd.save(orderMaster);
        if(orderMaster1 == null){
            log.error("【订单支付】 更新失败，orderMaster={}",orderMaster);
            throw new WXOrderException(ResultEnums.ORDER_UPDATE_PAY_EROR);
        }
        return orderMasterDTO;
    }
}
