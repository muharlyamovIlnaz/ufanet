package com.effective.mobile.tskmngmntsystm.util;

import com.effective.mobile.tskmngmntsystm.enums.Role;
import com.effective.mobile.tskmngmntsystm.models.TaskEntity;
import com.effective.mobile.tskmngmntsystm.models.UserEntity;
import com.effective.mobile.tskmngmntsystm.repository.TaskRepository;
import com.effective.mobile.tskmngmntsystm.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserUtil {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public boolean isMatching(Long id) {
        TaskEntity currentTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задача с id " + id + " не найдена"));
        Long performerId = currentTask.getPerformerId();
        return performerId.equals(getCurrentUserId());
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
