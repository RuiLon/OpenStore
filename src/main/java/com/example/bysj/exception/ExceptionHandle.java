package com.example.bysj.exception;

import com.example.bysj.util.RuleUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionHandle {

    /**
     * 自定义异常捕获类
     * @param cSe
     * @return
     */
    @ExceptionHandler(ConsumerServerExce.class)
    @ResponseBody
    public RuleUtil handle(ConsumerServerExce cSe)
    {
       return RuleUtil.error(cSe.getCode(),cSe.getMessage());
    }
}
