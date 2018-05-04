package com.txhl.wxorder.controller;

import com.txhl.wxorder.constants.Constants;
import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.OrderMasterService;
import com.txhl.wxorder.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

/**
 * 卖家端订单
 *
 * @author Administrator
 * @create 2018-04-25 10:18
 */
@Controller
@Slf4j
@RequestMapping(value = "/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private PushMessageService pushMessageService;

    /**
     * @param: page,size
     * describe: 所有卖家订单
     * creat_user: sl
     * creat_date: 2018/4/25
     * creat_time: 16:55
     **/
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findList(PageRequest.of(page-1,size));
        map.put("orderMasterDTOPage",orderMasterDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/order/list",map);
    }

    /**
     * @param: orderId
     * describe: 卖家取消订单
     * creat_user: sl
     * creat_date: 2018/4/25
     * creat_time: 16:55
     **/
    @GetMapping(value = "cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        OrderMasterDTO orderMasterDTO = null;
        try {
            orderMasterDTO = orderMasterService.findOne(orderId);
            orderMasterService.cancel(orderMasterDTO);
        } catch (WXOrderException e){
            log.error("【卖家取消订单】 发生异常 {}",e);
            map.put("msg",e.getMessage());
            map.put("url", Constants.ORDER_REDIRECT_URL);
            return new ModelAndView(Constants.MODELVIEW_ERROR,map);
        }

        map.put("msg",ResultEnums.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url",Constants.ORDER_REDIRECT_URL);
        return new ModelAndView(Constants.MODELVIEW_SUCCESS,map);
    }

    /**
     * @param: orderId
     * describe: 订单详情
     * creat_user: sl
     * creat_date: 2018/4/25
     * creat_time: 18:05
     **/
    @GetMapping(value = "detail")
    public ModelAndView detail(@RequestParam(value = "orderId") String orderId,
                               Map<String,Object> map){
        OrderMasterDTO orderMasterDTO = null;
        try {
            orderMasterDTO = orderMasterService.findOne(orderId);
        } catch (WXOrderException e){
            log.error("【卖家订单详情】 发生异常 {}",e);
            map.put("msg",e.getMessage());
            map.put("url",Constants.ORDER_REDIRECT_URL);
            return new ModelAndView(Constants.MODELVIEW_ERROR,map);
        }
        map.put("orderMasterDTO",orderMasterDTO);
        return new ModelAndView("/order/detail",map);
    }

    /**
     * @param: orderId
     * describe: 订单完结
     * creat_user: sl
     * creat_date: 2018/4/25
     * creat_time: 19:17
     **/
    @GetMapping(value = "finish")
    public ModelAndView finish(@RequestParam(value = "orderId") String orderId,
                               Map<String,Object> map){
        OrderMasterDTO orderMasterDTO = null;
        try {
            orderMasterDTO = orderMasterService.findOne(orderId);
            orderMasterService.finish(orderMasterDTO);
        } catch (WXOrderException e){
            log.error("【卖家订单完结】 发生异常 {}",e);
            map.put("msg",e.getMessage());
            map.put("url",Constants.ORDER_REDIRECT_URL);
            return new ModelAndView(Constants.MODELVIEW_ERROR,map);
        }
        // 推送消息
        pushMessageService.OrderStatus(orderMasterDTO);

        map.put("msg",ResultEnums.ORDER_FINISHED_SUCCESS.getMsg());
        map.put("url",Constants.ORDER_REDIRECT_URL);
        return new ModelAndView(Constants.MODELVIEW_SUCCESS,map);
    }
}
