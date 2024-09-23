package com.stver8935.user_service.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.models
 * @fileName : UserState
 * @since : 24. 8. 14.
 */

@Getter
@RequiredArgsConstructor
public enum UserState {
    WAIT("000","대기"),
    NORMAL("001","정상"),
    SUSPENDED("002","정지"),
    LEAVE("003","탈퇴"),
    DELETE("004","삭제");

    private final String key;
    private final String title;

    public static UserState findByKey(String key){
        return Arrays.stream(UserState.values())
                .filter(c -> c.getKey().equals(key))
                .findAny()
                .orElse(null);
    }
}
