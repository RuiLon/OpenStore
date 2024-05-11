package com.example.bysj.exception;

import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 自定义用户业务异常
 */
@Data
public class ConsumerServerExce extends RuntimeException{

    private String code;

    public ConsumerServerExce(String code,String msg)
    {
        super(msg);
        this.code = code;
    }
}
