package com.stver8935.user_service.security.jwt;

import com.stver8935.user_service.models.User;
import com.stver8935.user_service.models.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import io.jsonwebtoken.security.SecurityException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PublicKey;
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
     * 토큰 유효성 체크
     * @param token [access Token 타입에 해당하는 토큰
     * @return 유효성 여부
     */
    private boolean isValidateSymmKeyJWT(String token,SecretKey signKey,SecretKey encryptKey){
        final JwtParser parser = Jwts.parser()
                        .verifyWith(signKey)
                        .decryptWith(encryptKey)
                        .build();
        try {
            parser.parseSignedClaims(token);
            return true;
        }catch (MalformedJwtException
                | SecurityException
                | ExpiredJwtException
                | IllegalArgumentException e){
            log.info(" Invalid Symmetric Key Encryption JWT : {}",e.getMessage());
            return false;
        }
    }
    private boolean isValidateAsymmKeyJWT(String tokenStr,PublicKey publicKey,SecretKey encryptKey){
        try {
            Jwts.parser()
                    .verifyWith(publicKey)
                    .decryptWith(encryptKey)
                    .build()
                    .parseSignedClaims(tokenStr);
            return true;
        }catch (MalformedJwtException
                | SecurityException
                | ExpiredJwtException
                | IllegalArgumentException e){
            log.info(" Invalid Asymmetric Key Encryption JWT : {}",e.getMessage());
            return false;
        }
    }


    /**
     * 대칭키 암호화 키 생성
     * @param secretKeyStr 비밀키 문자열
     * @return SecretKey
     */
    public SecretKey getSignKey(@NonNull @NotEmpty String secretKeyStr){
        byte[] keyBytes = secretKeyStr.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 비 대칭키 암호화 키 생성
     * @param secretKeyStr 비밀키 문자열
     * @return SecretKey
     */
    public SecretKey getEncryptKey(@NonNull @NotEmpty String secretKeyStr){
        byte[] bytes = secretKeyStr.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(bytes,"AES");
    }

    /**
     * 비대칭 키 기반 으로 서명과 검증을 위한 키 페어 생성
     * @return KeyPair
     */
    public KeyPair generateAsymmetricKey(){
        return Jwts.SIG.RS256.keyPair().build();
    }

    /**
     * Access Token 발급
     * @param user 발급 할 계정의 정보
     * @return Access Token
     */
    public String generateAccessToken(User user){

        final SecretKey encryptKey    = getEncryptKey(jwtProperties.getAccessTokenEncryptKey());
        final ZonedDateTime currentDt = ZonedDateTime.now();
        final ZonedDateTime expiredDt = currentDt.plusSeconds(jwtProperties.getAccessTokenExpiredTimeMin());

        final Claims claims = Jwts.claims()
                .subject(user.getUserName())
                .add("email",user.getEmail())
                .add("phoneNum",user.getPhoneNum())
                .add("userRole",UserRole.USER)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(Date.from(currentDt.toInstant()))
                .expiration(Date.from(expiredDt.toInstant()))
                .build();

        return Jwts.builder()
                .claims(claims)
                .encryptWith(encryptKey,Jwts.ENC.A128CBC_HS256)
                .compact();
    }

    /**
     * Refresh Token 발급
     * @param user 발급 할 계정의 정보
     * @return Refresh Token
     */
    public String generateRefreshToken(@NotNull User user){

        final SecretKey signKey       = getSignKey(jwtProperties.getRefreshTokenSignKey());
        final SecretKey encryptKey    = getEncryptKey(jwtProperties.getRefreshTokenEncryptKey());
        final ZonedDateTime currentDt = ZonedDateTime.now();
        final ZonedDateTime expiredDt = currentDt.plusSeconds(jwtProperties.getRefreshTokenExpiredTimeMin());

        final Claims claims = Jwts.claims()
                .issuer(jwtProperties.getIssuer())
                .subject(user.getUserName())
                .issuedAt(Date.from(currentDt.toInstant()))
                .expiration(Date.from(expiredDt.toInstant()))
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(signKey)
                .encryptWith(encryptKey,Jwts.ENC.A128CBC_HS256)
                .compact();
    }

    /**
     * token 으로부터 메타데이터 정보 파싱
     *
     * @param token     [access Token , refresh Token] 두가지 타입의 토큰
     * @return 토큰 메타 데이터
     */
    private Jws<Claims> parseJws(@NotBlank String token,SecretKey signKey){
        try {
            return Jwts.parser()
                    .verifyWith(signKey)
                    .build()
                    .parseSignedClaims(token);
        }catch (SecurityException
                | ExpiredJwtException
                | MalformedJwtException
                | IllegalArgumentException e){
            log.error(" parseSignedToken Exception : {}",e.getMessage());
            return null;
        }
    }

    private Jwe<Claims> parseJwe(@NotBlank String token,SecretKey decryptKey){
        try {
            return Jwts.parser()
                    .decryptWith(decryptKey)
                    .build()
                    .parseEncryptedClaims(token);
        }catch (SecurityException
                | ExpiredJwtException
                | MalformedJwtException
                | IllegalArgumentException e){
            log.error(" parseSymmEncryptToken Exception : {}",e.getMessage());
            return null;
        }
    }


    private Jwt<?,?>  parseAsymmetricToken(@NotBlank String token, @NotNull PublicKey publicKey){
        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);
        }catch (SecurityException
                | ExpiredJwtException
                | MalformedJwtException
                | IllegalArgumentException e){
            log.info(" parseAsymmetricToken Exception : {}",e.getMessage());
            return null;
        }
    }

    public Jwe<Claims> parseAccessToken(@NotBlank String accessTokenStr){
        final SecretKey encryptKey = getEncryptKey(jwtProperties.getAccessTokenEncryptKey());
        return parseJwe(accessTokenStr,encryptKey);
    }

    public Jwt<?,?>  parseRefreshToken(@NotBlank String refreshTokenStr, @NotNull PublicKey publicKey){
        final SecretKey encryptKey  = getEncryptKey(jwtProperties.getAccessTokenEncryptKey());
        return parseJwe(refreshTokenStr,encryptKey);
    }

    public boolean isValidateAccessToken(String accessTokenStr){
        final SecretKey signKey = getSignKey(jwtProperties.getAccessTokenSignKey());
        final SecretKey encryptKey = getEncryptKey(jwtProperties.getAccessTokenEncryptKey());
        return isValidateSymmKeyJWT(accessTokenStr,signKey,encryptKey);
    }

    public boolean isValidateRefreshToken(String refreshTokenStr){
        final SecretKey signKey = getSignKey(jwtProperties.getRefreshTokenSignKey());
        final SecretKey encryptKey = getEncryptKey(jwtProperties.getAccessTokenEncryptKey());
        return isValidateSymmKeyJWT(refreshTokenStr,signKey,encryptKey);
    }
}
