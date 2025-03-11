package com.phantom.idm.repository;

import com.phantom.idm.entity.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDetailJpaRepository extends JpaRepository<UserDetailEntity, UUID> {

    Optional<UserDetailEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
