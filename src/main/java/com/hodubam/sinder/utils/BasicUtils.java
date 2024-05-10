package com.hodubam.sinder.utils;

import java.util.UUID;

public class BasicUtils {

    public static String createUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
