package com.txhl.wxorder.controller;

import com.google.gson.Gson;
import com.txhl.wxorder.config.ProjectUrlConfig;
import com.txhl.wxorder.constants.Constants;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 微信
 *
 * @author Administrator
 * @create 2018-04-09 16:10
 */
@Controller
@RequestMapping(value = "/wechat")
@Slf4j
public class WechatController {

    /**
     * 公众平台service
     */
    @Autowired
    private WxMpService wxMpService;

    /**
     * 开放平台Service
     */
    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * @param: returnUrl
     * describe: 微信公众平台授权
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 15:38
     **/
    @GetMapping(value = "authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){

        //1、配置
        //2、调用方法
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(
                              projectUrlConfig.getWxMPAuthorize()+"/wxorder/wechat/userInfo",
                                WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                                URLEncoder.encode(returnUrl));
        log.info("【微信认证】 跳转路径URL：{}",redirectUrl);
        return "redirect:"+redirectUrl;
    }

    /**
     * @param: code,state,returnUrl
     * describe: 获取用户信息
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 15:38
     **/
    @GetMapping(value = "/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
        Gson gson = new Gson();
        //获取access_token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        //获取用户信息
        WxMpUser wxMpUser = new WxMpUser();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            log.info("【微信用户信息】 {}",gson.toJson(wxMpUser));
        } catch (WxErrorException e){
            log.info("【微信网页授权】 {}", e);
            throw new WXOrderException(ResultEnums.WECHAT_MP_OAUTH.getCode(),e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openid;
    }

    /**
     * @param:
     * describe: 微信开放平台授权
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 15:39
     **/
    @GetMapping(value = "qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl")String returnUrl){
        String url = projectUrlConfig.getWxOPenAuthorize() + "/wxorder/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        log.info("【微信开放平台授权】 授权路径：{}",redirectUrl);
        return "redirect:"+redirectUrl;
    }

    /**
     * @param: code、state
     * describe: 微信开放平台获取用户信息
     * creat_user: sl
     * creat_date: 2018/4/28
     * creat_time: 14:34
     **/
    @GetMapping(value = "/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e){
            log.error("【微信开放平台授权】 异常原因: "+ e.getMessage());
            throw new WXOrderException(ResultEnums.WECHAT_OPEN_QRAUTH);
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:"+returnUrl+"?openid="+openid;
    }

    /**
     * @param:
     * describe: 设置菜单
     * creat_user: sl
     * creat_date: 2018/4/28
     * creat_time: 14:34
     **/
    @GetMapping(value = "/setMenu")
    public ModelAndView setMenu(Map<String,Object> map){

        WxMenu menu = new WxMenu();
        WxMenuButton button = new WxMenuButton();
        button.setName("外卖");
        button.setType("view");
        button.setUrl("http://wm.limit-tech.com");
        List<WxMenuButton> buttons = Arrays.asList(button);
        menu.setButtons(buttons);
        // 设置菜单
        try {
            wxMpService.getMenuService().menuCreate(menu);
        } catch (WxErrorException e){
            log.error("【微信菜单】 设置失败 {}",e);
            map.put("msg",ResultEnums.WECHAT_MENU_ERROR.getMsg());
            map.put("url",Constants.ORDER_REDIRECT_URL);
            return new ModelAndView(Constants.MODELVIEW_ERROR,map);
        }

        map.put("msg",ResultEnums.WECHAT_MENU_SUCCESS.getMsg());
        map.put("url",Constants.ORDER_REDIRECT_URL);
        return new ModelAndView(Constants.MODELVIEW_SUCCESS,map);
    }
}
