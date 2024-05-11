package com.example.bysj.util;

import com.example.bysj.constants.codeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleUtil {

    private String code; //状态码
    private String msg; //状态消息
    private Object data; //返回数据

    public static RuleUtil success()
    {
        return new RuleUtil(codeConstants.Code_200,"",null);
    }

    public static RuleUtil success(Object data)
    {
        return new RuleUtil(codeConstants.Code_200,"",data);
    }

    public static RuleUtil error(String code,String msg)
    {
        return new RuleUtil(code,msg,null);
    }

    public static RuleUtil error()
    {
        return new RuleUtil(codeConstants.Code_500,"系统错误",null);
    }
}
