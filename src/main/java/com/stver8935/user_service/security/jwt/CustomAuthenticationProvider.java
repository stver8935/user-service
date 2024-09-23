package com.stver8935.user_service.security.jwt;

import com.stver8935.user_service.security.services.UserDetailsServiceTmpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.jwt
 * @fileName : CustomAuthenticationProvider
 * @since : 2024. 8. 27.
 */
@Slf4j
@AllArgsConstructor
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceTmpl userDetailsServiceTmpl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String pwd = (String) authentication.getCredentials();

        UserDetails user = userDetailsServiceTmpl.loadUserByUsername(userName);

        if(!bCryptPasswordEncoder.matches(pwd,user.getPassword())){
            throw new BadCredentialsException(" bed pwd");
        }

        return new UsernamePasswordAuthenticationToken(userName,null,user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
