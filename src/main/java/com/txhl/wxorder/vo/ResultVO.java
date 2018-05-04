package com.txhl.wxorder.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回前端最外层对象
 * @author Administrator
 * @create 2018-04-01 20:32
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 2888427270164060189L;

    //返回码
    private Integer code;
    //消息
    private String msg;
    //数据
    private T data;
}
