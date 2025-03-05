package ru.ufanet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ufanet.models.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Query("UPDATE UserEntity u SET " +
            "u.name = COALESCE(:name, u.name), " +
            "u.phone = COALESCE(:phone, u.phone), " +
            "u.email = COALESCE(:email, u.email), " +
            "u.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE u.id = :id")
    int updateUser(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("email") String email
    );

    List<UserEntity> findByIdIn(List<Long> clientIds);
}