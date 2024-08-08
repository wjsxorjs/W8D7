package com.sist.jwt_ex1.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
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
        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now+1000L*seconds);

        JwtBuilder jwtBuilder = Jwts.builder()
                                    .subject("jmg")
                                    .expiration(accessTokenExpiresIn);

        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();

        while(it.hasNext()){
            String key = it.next();
            Object value = map.get(key);
            jwtBuilder.claim(key, value); // 개인정보 저장
        }

        return jwtBuilder.signWith(getSecretKey())
                         .compact(); // 개인정보가 저장된 builder로
                                     // Token 발행
    }
}
