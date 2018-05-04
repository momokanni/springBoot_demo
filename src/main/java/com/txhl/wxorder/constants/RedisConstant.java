package com.txhl.wxorder.constants;

/**
 * class_name: RedisConstant
 * package: com.txhl.wxorder.constants
 * describe: redis常量
 * creat_user: sl
 * creat_date: 2018/4/27
 * creat_time: 18:39
 **/
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE = 7200;//2小时
}
