package com.txhl.wxorder.controller;

import com.txhl.wxorder.config.ProjectUrlConfig;
import com.txhl.wxorder.constants.Constants;
import com.txhl.wxorder.constants.CookieConstant;
import com.txhl.wxorder.constants.RedisConstant;
import com.txhl.wxorder.entity.SellerInfo;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.service.SellerService;
import com.txhl.wxorder.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家
 *
 * @author Administrator
 * @create 2018-04-27 17:38
 */
@Slf4j
@Controller
@RequestMapping(value = "/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1、openid进行数据库匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo == null){
            map.put("msg", ResultEnums.SELLER_NOT_EXISTS.getMsg());
            map.put("url", Constants.ORDER_REDIRECT_URL);
            return new ModelAndView(Constants.MODELVIEW_ERROR,map);
        }
        //2、设置access_token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),
                                              openid,expire, TimeUnit.SECONDS);
        //3、设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);

        return new ModelAndView("redirect:"+ projectUrlConfig.getWxorder()+"/wxorder/seller/order/list" );
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map) {
        //1、从cookie查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie != null){
            //2、清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

            //3、清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        } else {
            log.error("【登出】 登出失败，cookie = null");
            throw new WXOrderException(ResultEnums.LOGIN_OUT_ERROR);
        }

        map.put("msg",ResultEnums.LOGIN_OUT_SUCCESS.getMsg());
        map.put("url",Constants.ORDER_REDIRECT_URL);

        return new ModelAndView(Constants.MODELVIEW_SUCCESS,map);


    }
}
