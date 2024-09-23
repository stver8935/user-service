package com.stver8935.user_service.security.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.dto
 * @fileName : LoginRequest
 * @since : 24. 8. 14.
 */

@Data
@SuperBuilder
public class LoginRequest {

    @NotEmpty(message = "login user name is empty")
    private String userName;

    @NotEmpty(message = "login user password is empty")
    private String password;

}
