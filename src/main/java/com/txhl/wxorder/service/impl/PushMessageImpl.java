package com.txhl.wxorder.service.impl;

import com.txhl.wxorder.config.WechatAccountConfig;
import com.txhl.wxorder.dto.OrderMasterDTO;
import com.txhl.wxorder.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 消息推送实现类
 *
 * @author Administrator
 * @create 2018-04-27 21:33
 */
@Service
@Slf4j
public class PushMessageImpl implements PushMessageService{

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    /**
     * @param: orderMasterDTO
     * describe: 推送订单状态变更消息
     * creat_user: sl
     * creat_date: 2018/4/28
     * creat_time: 13:41
     **/
    @Override
    public void OrderStatus(OrderMasterDTO orderMasterDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus").toString());
        templateMessage.setToUser("openid");

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货。"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "18868812345"),
                new WxMpTemplateData("keyword3", orderMasterDTO.getOrderId()),
                new WxMpTemplateData("keyword4", orderMasterDTO.getOrderStatusEnums().getMsg()),
                new WxMpTemplateData("keyword5", "￥" + orderMasterDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临！"));
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e){
            log.error("【微信模板消息】 发送失败,{}",e);
        }
    }
}
