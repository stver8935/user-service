package com.stver8935.rest_api.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

/**
 * 예외 메시지 다국어 처리를 위한 클래스
 *
 * @author : hangihyeong
 * @ packageName : com.stver8935.rest_api.utils
 * @ fileName : ExceptionMessageAccessor
 * @since : 24. 8. 15.
 */
@Service
public class ExceptionMessageAccessor {

    private final MessageSource messageSource;

    ExceptionMessageAccessor(
            // 예외 메시지 파일 명을 적어준다.
            @Qualifier("exceptionMessageSource") MessageSource messageSource){
        this.messageSource = messageSource;
    }

    /**
     * 예외 메시지 반환
     * @param locale 지역
     * @param key 메이지 파일 내 키값
     * @param param 파라미터
     * @return 메시지 문자열
     */
    public String getMessage(Locale locale, String key, Object... param){
        if(Objects.isNull(locale)){
            return messageSource.getMessage(key,param, Constants.KOREAN_LOCALE);
        }
        return messageSource.getMessage(key,param,locale);
    };
}
