package com.util;

import java.util.UUID;

public class codeUtil {
    public static String getCode(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
