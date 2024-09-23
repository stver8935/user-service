package com.stver8935.user_service.utils;

import java.util.Locale;

/**
 * 상수 모음 클래스
 *
 * @author : hangihyeong
 * @PackageName : com.stver8935.rest_api.utils
 * @fileName : Constraints
 * @since : 24. 8. 15.
 */
public final class Constants {

    /**
     * 한국 지역 설정 값
     */
    public static final Locale KOREAN_LOCALE = new Locale.Builder().setLanguage("ko").setRegion("KR").build();

    private Constants(){
        throw new UnsupportedOperationException();
    }
}
