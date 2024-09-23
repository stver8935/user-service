package com.stver8935.rest_api.controllers;

import com.stver8935.rest_api.security.dto.RegistrationRequest;
import com.stver8935.rest_api.security.dto.RegistrationResponse;
import com.stver8935.rest_api.security.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.controllers
 * @fileName : RegistrationUserController
 * @since : 2024. 8. 19.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/registration")
public class AuthController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody RegistrationRequest request){
        final RegistrationResponse response = userService.registration(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
