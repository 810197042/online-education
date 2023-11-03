package com.cwx.servicebase.exceptionhandler;


import com.cwx.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 @author  *
 @date 2023/10/19$ 19:19$*
 @description: */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行特定异常处理");
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e) {
        log.error(e.getMessage());//写到log日志里
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }

}
