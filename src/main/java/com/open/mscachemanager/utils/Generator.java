package com.open.mscachemanager.utils;

import java.util.UUID;

public class Generator {

    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
