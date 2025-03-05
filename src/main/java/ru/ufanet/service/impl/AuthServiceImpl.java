package ru.ufanet.service;

import com.effective.mobile.tskmngmntsystm.dto.SignInRequest;
import com.effective.mobile.tskmngmntsystm.dto.SignUpRequest;
import com.effective.mobile.tskmngmntsystm.dto.TaskServiceResponse;
import com.effective.mobile.tskmngmntsystm.exception.CustomException;
import com.effective.mobile.tskmngmntsystm.mapper.UserMapper;
import com.effective.mobile.tskmngmntsystm.models.UserEntity;
import com.effective.mobile.tskmngmntsystm.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public TaskServiceResponse<String> signUp(SignUpRequest request) {
        try {
            UserEntity userEntity = userMapper.toUserEntity(request);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);
            log.info("Пользователь создан");
            return TaskServiceResponse.ok("Клиент сохранен");
        } catch (Exception e) {
            log.error("Пользователь с таким Email уже существует. Error: {}", e.getMessage());
            throw new CustomException("Пользователь с таким Email уже существует", e);
        }
    }

    @Override
    public TaskServiceResponse<String> signIn(SignInRequest request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail()).orElseThrow(EntityNotFoundException::new);

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            log.error("Некорректный пароль");
            return TaskServiceResponse.notOk("Некорректный пароль", HttpStatus.BAD_REQUEST);
        }
        String token = jwtService.generateToken(userEntity);
        log.info("Токен успешно получен");
        return TaskServiceResponse.ok("Токен успешно получен", token);
    }
}
