package com.txhl.wxorder.exception;

import com.txhl.wxorder.enums.ResultEnums;
import lombok.Getter;

/**
 * 异常管理
 *
 * @author Administrator
 * @create 2018-04-02 14:03
 */
@Getter
public class WXOrderException extends RuntimeException {

    private Integer code;

    public WXOrderException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode() ;
    }

    public WXOrderException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }
}
