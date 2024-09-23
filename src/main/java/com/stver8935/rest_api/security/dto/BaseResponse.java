package com.stver8935.rest_api.security.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalTime;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.security.dto
 * @fileName : BaseResponse
 * @since : 2024. 8. 30.
 */

@Getter
@Setter
@SuperBuilder
public class BaseResponse {
    private HttpStatus status;
    private int code;
    private String message;
    private LocalTime timeStamp;
}
