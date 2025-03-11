package com.phantom.idm.repository;

import com.phantom.idm.entity.UserDetailEntity;
import com.phantom.idm.entity.UserPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPasswordJpaRepository extends JpaRepository<UserPasswordEntity, UUID> {

    Optional<UserPasswordEntity> findByUserDetail(UserDetailEntity userDetail);

}
