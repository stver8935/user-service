package com.stver8935.user_service.exceptions;

import com.stver8935.user_service.security.dto.BaseResponse;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.time.LocalTime.now;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.exceptions
 * @fileName : GlobalExceptionHandler
 * @since : 2024. 8. 30.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();
        String error = errors.stream().findFirst()
                .orElse("");

        BaseResponse response = BaseResponse.builder()
                .message(error)
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .timeStamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity<Object> handleUnexpectedRollbackException(UnexpectedRollbackException ex,WebRequest request){

        BaseResponse response = BaseResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .timeStamp(now())
                .build();
        return handleExceptionInternal(ex,response,new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
    }


}
