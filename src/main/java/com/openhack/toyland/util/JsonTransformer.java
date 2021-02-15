package com.openhack.toyland.util;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class JsonTransformer {
    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
