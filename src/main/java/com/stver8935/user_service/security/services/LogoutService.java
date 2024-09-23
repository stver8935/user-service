package com.stver8935.user_service.security.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * @author : hangihyeong
 * @ packageName : com.stver8935.rest_api.security.services
 * @ fileName : LogoutService
 * @since : 2024. 9. 2.
 */
public class LogoutService implements LogoutHandler {


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String contentType = request.getContentType();
        switch (contentType){
            case "application/json":

                break;
            case "":
                break;
        }

    }
}
