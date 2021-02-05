package com.openhack.toyland.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class JsonTransformer {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }
}
