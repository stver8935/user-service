package com.stver8935.rest_api.security.mapper;

import com.stver8935.rest_api.models.User;
import com.stver8935.rest_api.security.dto.AuthenticatedUserDto;
import com.stver8935.rest_api.security.dto.RegistrationRequest;
import com.stver8935.rest_api.security.services.UserDetailsServiceTmpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;

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
