package com.txhl.wxorder.aspect;

import com.txhl.wxorder.constants.CookieConstant;
import com.txhl.wxorder.constants.RedisConstant;
import com.txhl.wxorder.exception.WXOrderAuthorizeException;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Aop拦截
 *
 * @author Administrator
 * @create 2018-04-27 20:38
 */
@Aspect
@Component
@Slf4j
public class WxorderAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.txhl.wxorder.controller.Seller*.*(..))"+
    "&& !execution(public * com.txhl.wxorder.controller.SellerController.*(..))")
    public void verify(){};

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("【登录校验】 cookie中查不到token");
            throw new WXOrderAuthorizeException();
        }

        //redis查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】 Redis中查不到token");
            throw new WXOrderAuthorizeException();
        }
    };
}
