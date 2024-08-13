package com.stver8935.rest_api.configs;

import com.stver8935.rest_api.repositories.ExampleRepository;
import com.stver8935.rest_api.models.ExampleDataModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import java.util.ArrayList;

/**
 * Configuration Example
 *
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.configs
 * @fileName : ExampleConfig
 * @since : 24. 8. 6.
 */
@Slf4j
@Configuration
public class ExampleConfig {

    private ArrayList<ExampleDataModel> arrayList = new ArrayList<>();

    @Bean
    CommandLineRunner initData(ExampleRepository repository){

        for(int i=0, max = 5; i < max; i++){
            arrayList.add(new ExampleDataModel(i,"Test"+i));
        }

        return args -> {
            repository.saveAll(arrayList.stream().toList());
        };
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customPageable(){
        return p -> {
            p.setOneIndexedParameters(true);
            p.setMaxPageSize(100);
            p.setFallbackPageable(
                    PageRequest.of(
                            0,
                            30,
                            Sort.by("id").ascending()
                    )
            );
        };
    }

}
