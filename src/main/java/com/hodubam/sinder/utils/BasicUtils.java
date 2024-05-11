package com.hodubam.sinder.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class BasicUtils {

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String createUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String encrypt(String str){
        return encoder.encode(str);
    }
}
