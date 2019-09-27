package com.lanxin.util;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2019/9/26 0026.
 */
@ControllerAdvice
public class GlobalExcepyion {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Result handleException(){

        return Result.failed(10006,"you have not authorization");
    }
}
