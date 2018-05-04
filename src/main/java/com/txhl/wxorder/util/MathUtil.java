package com.txhl.wxorder.util;

/**
 * 计算
 *
 * @author Administrator
 * @create 2018-04-24 15:46
 */
public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;
    /**
     * @param:
     * describe: 比较2个金额是否相等
     * creat_user:
     * creat_date: 2018/4/24
     * creat_time: 15:48
     **/
    public static boolean equals(Double d1,Double d2){
        Double result = Math.abs(d1 - d2);
        if(result < MONEY_RANGE){
            return true;
        }else{
            return false;
        }
    }
}
