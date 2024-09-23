package com.stver8935.user_service.utils;

import com.stver8935.user_service.models.RegexPattern;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.utils
 * @fileName : Functions
 * @since : 24. 8. 14.
 */

@Slf4j
@Component
public class Functions<T,A> implements RegexPattern {

    /**
     * 비속어 패턴 맵
     */
    private final Map<String, Pattern> SLANG_PATTERNS = new HashMap<>();
    /**
     * 비속어 단어 모음
     */
    private final Set<String> SLANG_WORDS_SET = new HashSet<>(List.of(SLANG_WORDS));


    public Functions(){}

    /**
     * 정규식 패턴 초기화
     */
    @PostConstruct
    public void initRegex(){
        this.compileSlangPattern();;
    }

    /**
     * --
     */
    public void compileSlangPattern(){
        String patternTxt = buildSlangPatternText();
        for (String word : SLANG_WORDS_SET){
            String[] chars = word.split(" ");
            SLANG_PATTERNS.put(word,Pattern.compile(String.join(patternTxt, chars)));
        }
    }

    /**
     * 비속어 패턴 텍스트 생성
     * @return
     */
    private String buildSlangPatternText(){
        StringBuilder delimiterBuilder = new StringBuilder();
        for(String delimiter : SLANG_DELIMITERS){
            delimiterBuilder.append(Pattern.quote(delimiter));
        }
        delimiterBuilder.append("]*");
        return delimiterBuilder.toString();
    }

    /**
     * 비속어 체크
     */
    public boolean checkSlang(String txt){
        for(Pattern pattern : SLANG_PATTERNS.values()){
            if(pattern.matcher(txt).find()){
                return true;
            }
        }
        return false;
    }

    /**
     * 비속어 대체
     */
    public String changeSlang(String txt){
        for(Map.Entry<String,Pattern> e : SLANG_PATTERNS.entrySet()){
            String word = e.getKey();
            Pattern pattern = e.getValue();

            if(word.isEmpty())
                txt = txt.replace(word,SLANG_HIDE_WORD);

            txt = pattern.matcher(txt).replaceAll( wd ->
                    SLANG_HIDE_WORD.repeat(wd.group().length())
            );
        }
        return txt;
    }

    /**
     * 이메일 정규식 체크
     * @param txt 정규식 체크 할 문자열
     * @return boolean
     */
    public boolean checkEmail(String txt){
        Pattern p = Pattern.compile(EMAIL_REGEX);
        return p.matcher(txt).find();
    }
    /**
     * 아이디 정규식 체크
     * @param txt 아이디 문자열
     * @return boolean
     */
    public boolean checkId(String txt){
        Pattern p = Pattern.compile(ID_REGEX);
        return p.matcher(txt).find();
    }
    /**
     * 이름 정규식 체크
     * @param txt 이름 문자열
     * @return boolean
     */
    public boolean checkName(String txt){
        Pattern p = Pattern.compile(NAME_REGEX);
        return p.matcher(txt).find();
    }



}
