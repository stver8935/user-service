package com.stver8935.rest_api.security.jwt;

import com.stver8935.rest_api.configs.SecurityConfig;
import com.stver8935.rest_api.security.services.UserDetailsServiceTmpl;
import com.stver8935.rest_api.security.utils.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

/**
 * jwt 인증 필터 요청당 한번씩 실행
 *
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.jwt
 * @fileName : JwtAuthenticationFilter
 * @since : 24. 8. 19.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;

    private final UserDetailsServiceTmpl userDetailsService;


    /**
     * accessToken 검증 제외 경로 목록
     */

    private final List<String> excludedPath = Arrays.asList(
            "/user/login",
            "/user/registration"
    );

    /**
     * 필터를 통하지 않을 경로 지정
     * @param request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludedPath.contains(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 인증 관련 헤더를 가져온다
        final String header = request.getHeader(SecurityConstants.HEADER_STRING);
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        UserDetails user = null;
        String userName = null;
        String accessToken = null;

        if(Objects.nonNull(header) && header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            try{
               accessToken = header.replace(SecurityConstants.TOKEN_PREFIX, Strings.EMPTY);
               Jwe<Claims> claimsJws = jwtTokenManager.parseAccessToken(accessToken);
               Claims claims = claimsJws.getPayload();

               userName = claims.getSubject();
               user = userDetailsService.loadUserByUsername(userName);
            }catch(Exception e){
                log.error("Authentication Exception : {}", e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 인증 토큰 유효성 체크
        try {
            jwtTokenManager.isValidateAccessToken(accessToken);
        }catch (Exception e){
            log.info(" token valid Exception {}",e.getMessage());
            filterChain.doFilter(request,response);
            return;
        }

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 인증된 사용자 등록
        securityContext.setAuthentication(authentication);
        filterChain.doFilter(request,response);


    }
}
