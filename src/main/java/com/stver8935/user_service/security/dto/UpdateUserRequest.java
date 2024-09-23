package com.stver8935.user_service.security.dto;

import com.stver8935.user_service.models.RegexPattern;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.dto
 * @fileName : UpdateUserRequest
 * @since : 2024. 8. 30.
 */
@Data
public class UpdateUserRequest{
    @Pattern(regexp = "",message = "")
    private String password;
    @Pattern(regexp = RegexPattern.EMAIL_REGEX, message = "")
    private String phoneNum;
}
