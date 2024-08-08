package com.sist.jwt_ex1.jwt;

import java.util.Base64;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component // 내가 만들기 싫어, Spring 너가 만들어줘!
public class JwtProvider{
    @Value("${custom.jwt.secretKey}")
    private String secretkKeyCode;

    private SecretKey secretKey;

    public SecretKey getSecretKey(){
        if(secretKey == null){
            String encoding = Base64.getEncoder()
                                    .encodeToString(secretkKeyCode.getBytes());
            
            secretKey = Keys.hmacShaKeyFor(encoding.getBytes());
        }

        return secretKey;
    }

    public String genToken(Map<String, Object> map, int seconds){
        
    }
}
