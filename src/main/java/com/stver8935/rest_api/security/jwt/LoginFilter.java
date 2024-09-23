package com.stver8935.rest_api.security.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stver8935.rest_api.security.dto.LoginRequest;
import com.stver8935.rest_api.security.dto.LoginResponse;
import com.stver8935.rest_api.security.services.UserService;
import com.stver8935.rest_api.utils.ExceptionMessageAccessor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Parser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.jwt
 * @fileName : LoginFIlter
 * @since : 2024. 8. 27.
 */
@Slf4j
@AllArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private ExceptionMessageAccessor exceptionMessageAccessor;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals("application/json")) {
            try {
                return getAuthFromJsonReq(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final boolean isAuth = authResult.isAuthenticated();

        if(isAuth){
            String userName = authResult.getName();
            String password = (String) authResult.getCredentials();

            LoginResponse loginResponse = userService.requestLogin(
                    LoginRequest.builder()
                    .userName(userName)
                    .password(password)
                    .build()
            );

            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        final String contentType = request.getContentType();
        String userName = (contentType.equals("application/json"))? request.getParameter("username") : "";
        response.setContentType(contentType);
        response.getWriter().write(objectMapper.writeValueAsString(userService.LoginFail(userName)));
    }

    private Authentication getAuthFromJsonReq(HttpServletRequest request) throws RuntimeException, IOException {
        Map<String,String> loginReq = objectMapper.readValue(request.getInputStream(), new TypeReference<>() {});
        String username = loginReq.get("username");
        String userPwd = loginReq.get("password");

        assert username.isEmpty():exceptionMessageAccessor.getMessage(null,"data.isNull",username);
        assert userPwd.isEmpty():exceptionMessageAccessor.getMessage(null,"data.isNull",userPwd);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,userPwd,null);
        setDetails(request,authToken);
        return authenticationManager.authenticate(authToken);
    }
}
