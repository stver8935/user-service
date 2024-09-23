package com.stver8935.rest_api.controllers;
import com.stver8935.rest_api.models.ExampleDataModel;
import com.stver8935.rest_api.services.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Example
 *
 * @author          : hangihyeong
 * @packageName     : com.stver8935.rest_api.enums
 * @fileName        : ExampleDataCategory
 * @since           : 24. 8. 6.
 */
@RestController
@RequestMapping("/api2")
@Slf4j
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/Example")
    public ResponseEntity<Page<ExampleDataModel>> retrieveExample(
            Pageable pageable,
            @RequestParam("category") int category,
            @RequestParam("searchWord") String searchWord){

        Page<ExampleDataModel> resp = exampleService.retrieveExampleData(searchWord,pageable);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @PostMapping("/Example")
    public ResponseEntity<ExampleDataModel> registExample(){
        ExampleDataModel model = new ExampleDataModel(2,"Post Request Hello");
        return new ResponseEntity<>(model,HttpStatus.OK);
    }

    @DeleteMapping("/Example")
    public ResponseEntity<ExampleDataModel> DeleteExample(){
        ExampleDataModel model = new ExampleDataModel(3,"Delete Request Hello");
        return new ResponseEntity<>(model,HttpStatus.OK);
    }

    @PutMapping("/Example")
    public ResponseEntity<ExampleDataModel> updateExample(){
        ExampleDataModel model = new ExampleDataModel(4,"Put Request Hello");
        return new ResponseEntity<>(model,HttpStatus.OK);
    }

    @PatchMapping("/Example/{id}")
    public ResponseEntity<ExampleDataModel> updateExample(
            @PathVariable Long id,
            @RequestBody ExampleDataModel exampleDataModel
    ){
        ExampleDataModel d = exampleService.updateExampleData(id,exampleDataModel);
        return new ResponseEntity<>(d,HttpStatus.OK);
    }


    @GetMapping("/connection")
    public String testConnection() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return "Database connection is successful!";
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }



}
