package com.stver8935.rest_api.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * jwt 관련 속성
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.jwt
 * @fileName : JwtProperties
 * @since : 24. 8. 15.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * 토큰 발급자
     */
    private String issuer;

    /**
     * refreshToken 서명을 위한 키값
     */
    private String refreshTokenSignKey;
    /**
     * refreshToken 암호화를 위한 키값
     */
    private String refreshTokenEncryptKey;
    /**
     * refreshToken 만료 시간
     */
    private long refreshTokenExpiredTimeMin;

    /**
     * accessToken 서명을 위한 키값
     */
    private String accessTokenSignKey;
    /**
     * accessToken 암호화를 위한 키값
     */
    private String accessTokenEncryptKey;

    /**
     * accessToken 만료 시간
     */
    private long accessTokenExpiredTimeMin;
}
