package com.erocha.freeman.commons.utils;

import java.util.UUID;

public class UUIDGenerator {

    private UUIDGenerator(){

    }

    public static String generate() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
