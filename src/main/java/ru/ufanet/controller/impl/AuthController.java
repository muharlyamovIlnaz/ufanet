package ru.ufanet.controller.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.ufanet.controller.AuthApi;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SignInRequest;
import ru.ufanet.dto.SignUpRequest;
import ru.ufanet.service.AuthService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public PoolReservationServiceResponse<String> signUp(SignUpRequest request) {
        log.info("Получен запрос на регистрацию нового пользователя");
        return authService.signUp(request);
    }

    @Override
    public PoolReservationServiceResponse<String> signIn(SignInRequest request) {
        log.info("Получен запрос на авторизацию пользователя");
        return authService.signIn(request);
    }

}
