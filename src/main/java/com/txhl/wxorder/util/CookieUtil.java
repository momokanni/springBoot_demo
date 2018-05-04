package com.txhl.wxorder.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 *
 * @author Administrator
 * @create 2018-04-27 18:54
 */
public class CookieUtil {

    /**
     * @param: response、name、value、maxAge
     * describe: 插入cookie
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 19:24
     **/
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie = new Cookie("token",value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * @param: request、name
     * describe: 获取cookie
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 19:23
     **/
    public static Cookie get(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }else{
            return null;
        }
    }

    /**
     * @param: request
     * describe: cookie转map
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 19:24
     **/
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies !=null){
            for (Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
