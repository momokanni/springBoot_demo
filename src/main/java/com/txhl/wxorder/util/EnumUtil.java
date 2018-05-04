package com.txhl.wxorder.util;

import com.txhl.wxorder.enums.CodeEnums;

/**
 * 枚举工具类
 *
 * @author Administrator
 * @create 2018-04-25 15:23
 */
public class EnumUtil {

    public static <T extends CodeEnums> T getByCode(Integer code,Class<T> enumsclass){
        for (T each : enumsclass.getEnumConstants()){
            if (each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }
}
