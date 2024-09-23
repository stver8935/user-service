package com.stver8935.user_service.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.utils
 * @fileName : SecurityConstants
 * @since : 24. 8. 15.
 */
public class SecurityConstants {

    public static final String TOKEN_PREFIX = "Bearer";

    public static final String HEADER_STRING = "Authentication";


    private SecurityConstants(){
        throw new UnsupportedOperationException();
    }

    public static String getAuthenticatedUserName(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
