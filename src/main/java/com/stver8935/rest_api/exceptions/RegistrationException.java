package com.stver8935.rest_api.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 커스텀 등록 예외
 *
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.exceptions
 * @fileName : RegistrationException
 * @since : 24. 8. 15.
 */

@Getter
@RequiredArgsConstructor
public class RegistrationException extends RuntimeException{

    private final String errorMessage;

}
