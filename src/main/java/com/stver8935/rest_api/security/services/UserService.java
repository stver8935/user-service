package com.stver8935.rest_api.security.services;

import com.stver8935.rest_api.exceptions.RegistrationException;
import com.stver8935.rest_api.models.User;
import com.stver8935.rest_api.models.UserRole;
import com.stver8935.rest_api.repositories.UserRepository;
import com.stver8935.rest_api.security.dto.*;
import com.stver8935.rest_api.security.jwt.JwtTokenManager;
import com.stver8935.rest_api.security.mapper.UserMapper;
import com.stver8935.rest_api.utils.ExceptionMessageAccessor;
import com.stver8935.rest_api.utils.GeneralMessageAccessor;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.time.LocalTime.now;

/**
 * 유저 관련 서비스
 *
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.services
 * @fileName : UserService8
 * @since : 24. 8. 14.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;
    private final ExceptionMessageAccessor exceptionMessageAccessor;
    private final GeneralMessageAccessor generalMessageAccessor;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    /**
     * 유저 아이디로 인증된 유저 데이터 반환
     * @param userName 유저 아이디
     * @return 인증된 유저 데이터 반환
     */
    public AuthenticatedUserDto findAuthenticatedUserByUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return userMapper.convertToAuthenticateUserDto(
                user.orElseThrow(
                        ()->
                                new NoSuchElementException(" user not found : "+userName)));
    }


    public LoginResponse requestLogin(LoginRequest request){
        final String username = request.getUserName();
        AuthenticatedUserDto authUserDto = findAuthenticatedUserByUserName(username);

        String accessToken = "";
        String refreshToken = "";
        String respMsg = "";

        if(authUserDto != null){
            User user = userMapper.convertToUser(authUserDto);
            accessToken = jwtTokenManager.generateAccessToken(user);
            refreshToken = jwtTokenManager.generateAccessToken(user);
            respMsg = "login Success !";
        }else{
            respMsg = "login fail !";
        }

        return LoginResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message(respMsg)
                .build();
    }

    @Transactional
    public LoginResponse LoginFail(String userName){
        userRepository.increaseLoginFailCount(userName);

        final int cnt = userRepository.findLoginFailCountByUserName(userName);

        return LoginResponse.builder()
                .message(5<=cnt?"login Lock ":" login Fail")
                .build();
    }

    @Transactional
    public LoginResponse increaseLoginFailCount(String userName){
        final int failCount = userRepository.increaseLoginFailCount(userName);
        log.info(" login fail count : {}",failCount);

        return LoginResponse.builder()
                .message(5<=failCount?"login Lock ":" login Fail")
                .build();
    }


    /**
     * 이메일 존재 여부 체크
     * @param email 이메일 주소
     */
    private void checkEmail(String email){
        final boolean isExistEmail = true;//userRepository.existsByEmail(email);

        if(isExistEmail){
            String exceptionMessage = exceptionMessageAccessor.getMessage(null,"~",email);
            throw new RegistrationException(exceptionMessage);
        }
    }

    /**
     * 핸드폰 번호 존재 여부 체크
     * @param phoneNum 핸드폰 번호
     */
    private void checkPhoneNum(String phoneNum){
        final boolean isExistPhoneNum = true;//userRepository.existsByPhoneNum(phoneNum);

        if(isExistPhoneNum){
            String exceptionMessage = exceptionMessageAccessor.getMessage(null,"",phoneNum);
            throw new RegistrationException(exceptionMessage);
        }
    }

    /**
     * 유저 아이디 존재 여부 체크
     * @param userId 유저 아이디
     */
    private void checkUserId(String userId){
        final boolean isExistUserId = true;//userRepository.existsByUserId(userId);
        if(isExistUserId){
            String exceptionMessage = exceptionMessageAccessor.getMessage(null,"exception");
            throw new RegistrationException(exceptionMessage);
        }
    }

    /**
     * 유저 유효성 검사
     * @param request 유저 등록 요청 데이터
     */
    public void validateUser(RegistrationRequest request){
        checkUserId(request.getUserName());
        checkEmail(request.getEmail());
        checkPhoneNum(request.getPhoneNum());
    }

    /**
     * 유저 등록 함수
     * @param request 등록 요청 파라미터
     * @return 유저 등록 요청 반환 데이터
     */
    @Transactional
    public RegistrationResponse registration(RegistrationRequest request){
        try {
            // 유저가 존재할 때나 필수 값이 빠졌을 때 발생하는 예외를 처리할 수 있습니다.

            log.info(" request {}",request.toString());
            User user = userMapper.convertToUser(request);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole(UserRole.USER);

            userRepository.saveAndFlush(user);

            return RegistrationResponse.builder()
                    .status(HttpStatus.CREATED)
                    .code(HttpStatus.CREATED.value())
                    .message("User registration successful!")
                    .timeStamp(now())
                    .build();

        } catch (DataIntegrityViolationException ex) {
            // 데이터 무결성 위반이 발생한 경우, 예외 처리
            throw new UnexpectedRollbackException(exceptionMessageAccessor.getMessage(null,"data.integrity"));
        }
    }
}
