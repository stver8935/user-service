package com.stver8935.rest_api.utils;

/**
 * 정규식 채턴을 정의.
 *
 * @author : hangihyeong
 * @ packageName : com.stver8935.rest_api.components
 * @ fileName : aa
 * @since : 24. 8. 14.
 */
public interface RegexPattern {

    /**
     * 아이디 정규식 패턴
     */
    public static final String ID_REGEX = "^[\\w\\]{5,20}$";
    /**
     * 이메일 정규식 패턴
     */
    public static final String EMAIL_REGEX = "^[\\w.%+\\-]+@[\\w.\\-]+[A-Za-z]{2,3}$";
    /**
     *  계정 이름 정규식 패턴
     */
    public static final String NAME_REGEX = "^[가-힣\\A-Za-z]+$";
    /**
     * 비속어 셋
     */
    public static final String[] SLANG_WORDS = {"","",""};
    /**
     * 비속어 변형 사용을 막기 위한 리스트
     */
    public static final String[] SLANG_DELIMITERS = {" ",",",".","!","@","1"};
    /**
     * 비속어 처리 후 보여질 문자열
     */
    public static final String SLANG_HIDE_WORD = "*";
}
