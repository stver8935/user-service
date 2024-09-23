package com.stver8935.user_service.services;

import com.stver8935.user_service.repositories.ExampleRepository;
import com.stver8935.user_service.models.ExampleDataModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Example
 *
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.services
 * @fileName : ExampleService
 * @since : 24. 8. 6.
 */
@Service
@Slf4j
public class ExampleService {

    @Autowired
    private ExampleRepository exampleRepository;


    public Page<ExampleDataModel> retrieveExampleData(String searchWord,Pageable pageable){
        if(searchWord == null || searchWord.isEmpty()){
            return exampleRepository.findAll(pageable);
        }
        return exampleRepository.findByTextContaining(searchWord,pageable);
    }

    public ExampleDataModel createExampleData(ExampleDataModel exampleDataModel) throws IllegalArgumentException{
        Optional<ExampleDataModel> optionalData = exampleRepository.findById(exampleDataModel.getId());
        if(optionalData.isPresent()){
            throw new IllegalArgumentException(" ExampleData Already Exist! ");
        }else{
            return exampleRepository.save(exampleDataModel);
        }
    }

    public ExampleDataModel updateExampleData(long id , ExampleDataModel exampleDataModel){
        Optional<ExampleDataModel> optionalExampleDataModel = exampleRepository.findById(id);
        if(optionalExampleDataModel.isPresent()){

            return exampleRepository.save(exampleDataModel);
        }else{
            throw new IllegalArgumentException(" ExampleData Not Exist! ");
        }
    }


    public void deleteExampleData(ExampleDataModel exampleDataModel){
        exampleRepository.delete(exampleDataModel);
    }
}
