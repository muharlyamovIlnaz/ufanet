package com.effective.mobile.tskmngmntsystm.controller.impl;

import com.effective.mobile.tskmngmntsystm.controller.AuthApi;
import com.effective.mobile.tskmngmntsystm.dto.SignInRequest;
import com.effective.mobile.tskmngmntsystm.dto.SignUpRequest;
import com.effective.mobile.tskmngmntsystm.dto.TaskServiceResponse;
import com.effective.mobile.tskmngmntsystm.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public TaskServiceResponse<String> signUp(SignUpRequest request) {
        log.info("Получен запрос на регистрацию нового пользователя");
        return authService.signUp(request);
    }

    @Override
    public TaskServiceResponse<String> signIn(SignInRequest request) {
        log.info("Получен запрос на авторизацию пользователя");
        return authService.signIn(request);
    }

}
