package com.stver8935.rest_api.security.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.dto
 * @fileName : LoginReponse
 * @since : 24. 8. 14.
 */

@Getter
@Setter
@SuperBuilder
public class LoginResponse extends BaseResponse{
    private String accessToken;
    private String refreshToken;
}
