package com.example.bysj.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtil {

    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;


    public static String genToken(String id,String sign){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create().withAudience(id)
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(sign));
    }
}
