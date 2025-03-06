package ru.ufanet.util;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ufanet.enums.Role;
import ru.ufanet.models.UserEntity;
import ru.ufanet.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserUtil {

    private final UserRepository userRepository;


    public boolean isMatching(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с " + id + " нет"));
        Role userRole = userEntity.getRole();
        return userRole.equals(getCurrentUserRole());
    }


    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("Ошибка при получении id пользователя");
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }
        String email = authentication.getName();
        UserEntity userByEmail = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("пользователь не найден"));
        log.info("id текущего пользователя = {}", userByEmail.getId());
        return userByEmail.getId();
    }


    public Role getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }

        String roleName = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new IllegalStateException("Роль не найдена"));
        try {
            log.info("Роль текущего пользователя {}", Role.valueOf(roleName));
            return Role.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            log.error("Не удалось определить роль пользователя");
            throw new IllegalStateException("Некорректная роль пользователя: " + roleName, e);
        }
    }
}
