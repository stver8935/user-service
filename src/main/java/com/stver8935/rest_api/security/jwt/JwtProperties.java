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

    private String issuer;

    private String secretKey;

    private long expiredTime;

    private String refreshTokenSecretKey;

    private long refreshTokenExpiredTime;

    private String accessTokenSecretKey;

    private long accessTokenExpiredTime;
}
