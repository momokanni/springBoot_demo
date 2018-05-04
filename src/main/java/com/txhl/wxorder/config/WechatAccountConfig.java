package com.txhl.wxorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 微信账号
 *
 * @author Administrator
 * @create 2018-04-09 19:59
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 公众平台ID
     */
    private String mpAppId;
    /**
     * 公众平台秘钥
     */
    private String mpAppSecret;
    /**
     * 开放平台ID
     */
    private String openAppId;
    /**
     * 开放平台秘钥
     */
    private String openAppSecret;

    /**
     * 商户ID
     */
    private String mchId;
    /**
     * 商户密钥
     */
    private String mchKey;
    /**
     * 商户证书地址
     */
    private String keyPath;
    /**
     * 支付回调地址
     */
    private String notifyUrl;
    /**
     * 微信模板ID
     */
    private Map<String,String> templateId;

}
