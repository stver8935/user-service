package com.stver8935.rest_api.security.jwt;

import com.stver8935.rest_api.models.User;
import com.stver8935.rest_api.models.UserRole;
import com.stver8935.rest_api.security.dto.AuthenticatedUserDto;
import com.stver8935.rest_api.security.dto.LoginRequest;
import com.stver8935.rest_api.security.dto.LoginResponse;
import com.stver8935.rest_api.security.mapper.UserMapper;
import com.stver8935.rest_api.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.jwt
 * @fileName : JwtTokenService
 * @since : 24. 8. 14.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenService {

    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;
    private final UserMapper userMapper;

    /**
     * 로그인 Response 반환
     * TODO 유저 아이디, 이메일 , 핸드폰 로그인 , sns 로그인 고려
     * @param loginRequest 로그인 요청 파라미터
     * @return LoginResponse
     */
    public LoginResponse getLoginResponse(LoginRequest loginRequest){

        final String username = loginRequest.getUserName();
        AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUserName(username);

        User user = userMapper.convertToUser(authenticatedUserDto);

        String accessToken = jwtTokenManager.generateAccessToken(user);
        String refreshToken = jwtTokenManager.generateAccessToken(user);


        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message(" login Success !")
                .build();

    }
}
