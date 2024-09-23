package com.stver8935.user_service.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.AbstractMessageSource;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author : hangihyeong
 * @ packageName : com.stver8935.rest_api.utils
 * @ fileName : YamlMessageSource
 * @since : 2024. 8. 19.
 */
@Slf4j
@RequiredArgsConstructor
public class YamlMessageSource extends AbstractMessageSource {

    private final String baseName;


    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        Yaml yaml = new Yaml();
        String fileName = String.format("%s_%s.yml",baseName,locale.toString());


        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            Map<String, Object> yamlMap = yaml.load(reader);
            if (yamlMap.containsKey(code)) {
                return new MessageFormat((String) yamlMap.get(code), locale);
            }
        } catch (Exception e) {
            log.error("Failed to load Yaml message source file: {}", fileName, e);
        }

        return null;
    }
}
