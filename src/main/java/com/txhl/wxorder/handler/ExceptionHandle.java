package com.txhl.wxorder.handler;

import com.txhl.wxorder.config.ProjectUrlConfig;
import com.txhl.wxorder.enums.ResultEnums;
import com.txhl.wxorder.exception.WXOrderAuthorizeException;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.util.ResultUtils;
import com.txhl.wxorder.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常捕获
 *
 * @author Administrator
 * @create 2018-04-03 20:10
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    /**
     * @param:
     * describe: 拦截登录异常
     * creat_user: sl
     * creat_date: 2018/4/27
     * creat_time: 20:57
     **/
    @ExceptionHandler(value = WXOrderAuthorizeException.class)
    public ModelAndView handelrAuthExpection(){
        return new ModelAndView("redirect:"
        .concat(projectUrlConfig.getWxOPenAuthorize())
        .concat("/wxorder/wechat/qrAuthorize?returnUrl=")
        .concat(projectUrlConfig.getWxorder())
        .concat("/wxorder/seller/login"));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResultVO handle(Exception e){
        if(e instanceof WXOrderException){
            WXOrderException wxOrderException = (WXOrderException) e;
            return ResultUtils.error(wxOrderException.getCode(),wxOrderException.getMessage());
        }
        log.error("系统错误：{}",e);
        return ResultUtils.error(ResultEnums.UNKONW.getCode(),ResultEnums.UNKONW.getMsg());
    }
}
