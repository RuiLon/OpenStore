package com.example.bysj.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.bysj.constants.codeConstants;
import com.example.bysj.enity.Consumer;
import com.example.bysj.exception.ConsumerServerExce;
import com.example.bysj.mapper.ArrangerMapper;
import com.example.bysj.mapper.ConsumerMapper;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private ArrangerMapper arrangerMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod))
        {
            return true;
        }
        if(StringUtil.isNullOrEmpty(token))
        {
            throw new ConsumerServerExce(codeConstants.Code_401,"没有权限");
        }

        String id;

        try{
            id = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException je)
        {
            throw new ConsumerServerExce(codeConstants.Code_401,"验证失败");
        }

        Consumer consumer = consumerMapper.readOne(Integer.valueOf(id));

        if(consumer == null)
        {
            throw new ConsumerServerExce(codeConstants.Code_600,"无相关用户");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(consumer.getPassword())).build();

        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException jve)
        {
            throw new ConsumerServerExce(codeConstants.Code_401,"密码不正确");
        }
        return true;
    }
}
