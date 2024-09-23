package com.stver8935.user_service.security.mapper;

import com.stver8935.user_service.models.User;
import com.stver8935.user_service.security.dto.AuthenticatedUserDto;
import com.stver8935.user_service.security.dto.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.mapper
 * @fileName : UserMapper
 * @since : 24. 8. 14.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    User convertToUser(RegistrationRequest request);

    @Mapping(source = "userName", target = "userName")
    AuthenticatedUserDto convertToAuthenticateUserDto(User user);

    @Mapping(source = "userName", target = "userName")
    User convertToUser(AuthenticatedUserDto authenticatedUserDto);

}
