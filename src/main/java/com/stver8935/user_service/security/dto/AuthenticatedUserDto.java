package com.stver8935.user_service.security.dto;

import com.stver8935.user_service.models.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.dto
 * @fileName : AuthenticatedUserDto
 * @since : 24. 8. 14.
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDto {
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
