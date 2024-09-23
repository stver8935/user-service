package com.stver8935.user_service.security.services;

import com.stver8935.user_service.repositories.UserRepository;
import com.stver8935.user_service.security.dto.AuthenticatedUserDto;
import com.stver8935.user_service.security.dto.CustomUserDetails;
import com.stver8935.user_service.utils.ExceptionMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.services
 * @fileName : UserDetailsServiceTmpl
 * @since : 24. 8. 16.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceTmpl implements UserDetailsService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final ExceptionMessageAccessor exceptionMessageAccessor;

    @Override
    public UserDetails loadUserByUsername(String userName) {

        AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUserName(userName);

        // TODO -- 나중에 확인 할것 Exception As..
        if(Objects.isNull(authenticatedUserDto)){
            // TODO exception Message Key 값 정의 후 삽입
            throw new UsernameNotFoundException(" User not Found Exception ");
        }

        return new CustomUserDetails(authenticatedUserDto);

    }
}
