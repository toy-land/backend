package com.openhack.toyland.dto;

import lombok.Getter;

@Getter
public class SimpleSuccessResponse {

    private String message;

    public SimpleSuccessResponse(String message) {
        this.message = message;
    }
}
