package com.stver8935.user_service.security.dto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.dto
 * @fileName : CustomUserDetails
 * @since : 2024. 8. 27.
 */
@AllArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetails {


    private final AuthenticatedUserDto user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        return collection;
    }

    @Override
    public String getPassword() {
        log.info(" getPassword : {}",user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        log.info(" getUsername : {} ",user.getUserName());
        return user.getUserName();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
