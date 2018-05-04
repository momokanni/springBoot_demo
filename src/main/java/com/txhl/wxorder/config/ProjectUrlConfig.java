package com.txhl.wxorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 项目路径配置类
 *
 * @author Administrator
 * @create 2018-04-27 17:17
 */
@Data
@Component
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrlConfig {

    /**
     * 微信公众平台授权URL
     */
    public String wxMPAuthorize;

    /**
     * 微信开放平台授权URL
     */
    public String wxOPenAuthorize;

    /**
     * 系统根路径
     */
    public String wxorder;

}
