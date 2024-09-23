package com.stver8935.rest_api.security.dto;

import com.stver8935.rest_api.models.RegexPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.mapstruct.Builder;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.dto
 * @fileName : RegistrationRequest
 * @since : 24. 8. 14.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Data
public class RegistrationRequest {
    @NotBlank(message = "userName value not exist")
    private String userName;
    @Pattern(regexp = RegexPattern.EMAIL_REGEX,message = "not valid pattern email")
    @NotBlank(message = "email value not exist")
    private String email;
    @NotBlank(message = "phone number value not exist")
    private String phoneNum;
    @NotBlank(message = "password value not exist")
    private String password;
}
