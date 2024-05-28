package com.hodubam.sinder.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

public class TokenUtil {

    private static final String SECRET_KEY = "sinder_secret_key_ipnida";
    private static final long ACCESS_TOKEN_VALIDITY = 5 * 60 * 1000; // 5분
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7일

    public static String generateAccessToken(String username) {
        return generateToken(username, ACCESS_TOKEN_VALIDITY);
    }

    public static String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_TOKEN_VALIDITY);
    }

    private static String generateToken(String username, long validity) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
