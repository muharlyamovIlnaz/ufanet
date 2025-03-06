package ru.ufanet.service.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ufanet.dto.PoolReservationServiceResponse;
import ru.ufanet.dto.SignInRequest;
import ru.ufanet.dto.SignUpRequest;
import ru.ufanet.exception.CustomException;
import ru.ufanet.mapper.UserMapper;
import ru.ufanet.models.UserEntity;
import ru.ufanet.repository.UserRepository;
import ru.ufanet.service.AuthService;
import ru.ufanet.service.JwtService;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public PoolReservationServiceResponse<String> signUp(SignUpRequest request) {
        try {
            UserEntity userEntity = userMapper.toUserEntity(request);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);
            log.info("Пользователь создан");
            return PoolReservationServiceResponse.ok("Клиент сохранен");
        } catch (Exception e) {
            log.error("Пользователь с таким Email уже существует. Error: {}", e.getMessage());
            throw new CustomException("Пользователь с таким Email или Phone уже существует", e);
        }
    }

    @Override
    public PoolReservationServiceResponse<String> signIn(SignInRequest request) {
        try {
            UserEntity userEntity = userRepository.findByEmail(request.getEmail()).orElseThrow(EntityNotFoundException::new);

            if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
                log.error("Некорректный пароль");
                return PoolReservationServiceResponse.notOk("Некорректный пароль", HttpStatus.BAD_REQUEST);
            }
            String token = jwtService.generateToken(userEntity);
            log.info("Токен успешно получен");
            return PoolReservationServiceResponse.ok("Токен успешно получен", token);
        } catch (Exception e) {
            log.error("Ошибка при входе в систему. Error: {}", e.getMessage());
            throw new CustomException("Ошибка при входе в систему", e);
        }
    }
}
