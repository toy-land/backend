package com.openhack.toyland.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailParticipant {

    private String address;
    private String name;
    private String type = "R";
    private EmailParameters parameters;

    public EmailParticipant(String address, String name, long sleepDays) {
        this.address = address;
        this.name = name;
        this.parameters = new EmailParameters(sleepDays);
    }
}
