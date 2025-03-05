package com.effective.mobile.tskmngmntsystm.service;

import com.effective.mobile.tskmngmntsystm.dto.SignInRequest;
import com.effective.mobile.tskmngmntsystm.dto.SignUpRequest;
import com.effective.mobile.tskmngmntsystm.dto.TaskServiceResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    TaskServiceResponse<String> signUp(SignUpRequest request);

    TaskServiceResponse<String> signIn(SignInRequest request);
}
