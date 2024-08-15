package com.stver8935.rest_api.utils;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

/**
 * 일반 메시지 다국어 처리를 위한 클래스
 *
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.utils
 * @fileName : GeneralMessageAccessor
 * @since : 24. 8. 16.
 */
@Service
public class GeneralMessageAccessor {

    private final MessageSource messageSource;

    GeneralMessageAccessor(@Qualifier("") MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public String getMessage(Locale locale, String key, Object... params){
      if(Objects.isNull(locale)){
          return messageSource.getMessage(key,params,Constants.KOREAN_LOCALE);
      }
      return messageSource.getMessage(key,params,locale);
    };




}
