package ru.ufanet.service;

import org.springframework.stereotype.Service;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SignInRequest;
import ru.ufanet.dto.SignUpRequest;


public interface AuthService {
    PoolReservationServiceResponse<String> signUp(SignUpRequest request);

    PoolReservationServiceResponse<String> signIn(SignInRequest request);
}
