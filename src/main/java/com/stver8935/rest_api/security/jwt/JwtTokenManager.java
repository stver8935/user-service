package com.stver8935.rest_api.security.jwt;

import com.stver8935.rest_api.models.User;
import com.stver8935.rest_api.models.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.jwt
 * @fileName : JwtTokenManager
 * @since : 24. 8. 14.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenManager {

    private final JwtProperties jwtProperties;

    /**
     * Access Token 발급
     * @param user 발급 할 계정의 정보
     * @return Access Token
     */
    public String generateAccessToken(User user){
        String name = user.getName();
        String email = user.getEmail();
        String phoneNum = user.getPhoneNum();
        UserRole userRole = user.getUserRole();
        String userId = user.getUserId();
        long id = user.getId();

        SecretKey secretKey = getAccessTokenSecretKey();
        long expiredTime = jwtProperties.getAccessTokenExpiredTime();
        String issuer = jwtProperties.getIssuer();

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expiredTime);

        Claims claims = Jwts.claims().build();
        claims.put("id",String.valueOf(id));
        claims.put("userId",userId);
        claims.put("email",email);
        claims.put("phoneNum",phoneNum);
        claims.put("userRole",userRole);

        return Jwts.builder()
                .subject(name)
                .claims(claims)
                .issuer(issuer)
                .issuedAt(Date.from(now.toInstant()))
                .expiration(Date.from(tokenValidity.toInstant()))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Refresh Token 발급
     * @param user 발급 할 계정의 정보
     * @return Refresh Token
     */
    public String generateRefreshToken(User user){
        String name = user.getName();
        String email = user.getEmail();
        String phoneNum = user.getPhoneNum();
        String userId = user.getUserId();
        long id = user.getId();

        SecretKey secretKey = getRefreshTokenSecretKey();
        long expiredTime = jwtProperties.getRefreshTokenExpiredTime();
        String issuer = jwtProperties.getIssuer();

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expiredTime);

        Claims claims = Jwts.claims().build();
        claims.put("id",String.valueOf(id));
        claims.put("userId",userId);
        claims.put("email",email);
        claims.put("phoneNum",phoneNum);

        return Jwts.builder()
                .subject(name)
                .claims(claims)
                .issuer(issuer)
                .issuedAt(Date.from(now.toInstant()))
                .expiration(Date.from(tokenValidity.toInstant()))
                .signWith(secretKey)
                .compact();
    }

    /**
     * token 으로부터 메타데이터 정보 파싱
     * @param token [access Token , refresh Token] 두가지 타입의 토큰
     * @param secretKey [access Token , refresh Token] 두가지 타입에 해당하는 SecretKey 값
     * @return 토큰 메타 데이터
     */
    public Claims parseClaims(String token,SecretKey secretKey){
        try {
            //SecretKey secretKey = getSecretKey();
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    /**
     * 토큰 유효성 체크
     * @param token [access Token , refresh Token] 타입에 해당하는 토큰
     * @param secretKey [access Token , refresh Token] 타입에 해당하는 secret Key
     * @return 유효성 여부
     */
    public boolean validateToken(String token, SecretKey secretKey){
        try {
            //SecretKey secretKey = getSecretKey();

            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        }catch(SecurityException e){
            log.info("Invalid Token",e);
        }catch (ExpiredJwtException e){
            log.info("Expired Token",e);
        }catch (UnsupportedJwtException e){
            log.info("Unsupported Token",e);
        }catch (IllegalArgumentException e){
            log.info("Token Claims String is empty",e);
        }
        return false;
    }


    public SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public SecretKey getAccessTokenSecretKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtProperties.getAccessTokenSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public SecretKey getRefreshTokenSecretKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtProperties.getRefreshTokenSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
