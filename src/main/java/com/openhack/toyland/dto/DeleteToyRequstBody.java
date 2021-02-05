package com.openhack.toyland.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class DeleteToyRequstBody {

    @NotEmpty
    private String password;
}
