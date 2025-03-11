package com.phantom.idm.repository;

import com.phantom.idm.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLoginJpaRepository extends JpaRepository<UserLoginEntity, UUID> {

}
