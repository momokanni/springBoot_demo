package com.txhl.wxorder.util;

import java.util.Random;

/**
 * ID随机生成
 *
 * @author Administrator
 * @create 2018-04-03 16:12
 */
public class KeyUtil {

    /**
     * describe: 生成唯一的主键(避免多线程ID重复)
     * creat_user: 孙林
     * creat_date: 2018/4/3
     * creat_time: 16:13
     **/
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer randomId = random.nextInt(900000)+100000;

        return System.currentTimeMillis() + String.valueOf(randomId);
    }
}
