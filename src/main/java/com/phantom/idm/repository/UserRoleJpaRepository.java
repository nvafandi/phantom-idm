package com.phantom.idm.repository;

import com.phantom.idm.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, UUID> {

    UserRoleEntity findByCode(String code);

}
