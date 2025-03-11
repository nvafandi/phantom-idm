package com.phantom.idm.service.impl;

import com.phantom.idm.dto.request.LoginRequest;
import com.phantom.idm.dto.request.RegisterRequest;
import com.phantom.idm.dto.response.BaseResponse;
import com.phantom.idm.entity.UserDetailEntity;
import com.phantom.idm.entity.UserPasswordEntity;
import com.phantom.idm.handler.BadRequestException;
import com.phantom.idm.handler.UnauthorizedException;
import com.phantom.idm.repository.UserDetailJpaRepository;
import com.phantom.idm.repository.UserPasswordJpaRepository;
import com.phantom.idm.repository.UserRoleJpaRepository;
import com.phantom.idm.service.AuthService;
import com.phantom.idm.utils.DateUtil;
import com.phantom.idm.utils.JwtUtil;
import com.phantom.idm.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserDetailJpaRepository userDetailJpaRepository;

    private final UserPasswordJpaRepository userPasswordJpaRepository;

    private final UserRoleJpaRepository userRoleJpaRepository;

    private final JwtUtil jwtUtil;

    public AuthServiceImpl(JwtUtil jwtUtil, UserDetailJpaRepository userDetailJpaRepository, UserPasswordJpaRepository userPasswordJpaRepository, UserRoleJpaRepository userRoleJpaRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailJpaRepository = userDetailJpaRepository;
        this.userPasswordJpaRepository = userPasswordJpaRepository;
        this.userRoleJpaRepository = userRoleJpaRepository;
    }

    @Override
    public BaseResponse<Object> register(RegisterRequest request) {
        BaseResponse<Object> response;

        try {

            if (userDetailJpaRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already exists");
            }

            var role = userRoleJpaRepository.findByCode(request.getRole());

            UserDetailEntity userDetail = new UserDetailEntity();
            userDetail.setEmail(request.getEmail());
            userDetail.setName(request.getName());
            userDetail.setUserRole(role);
            userDetail.setCreatedBy("system");
            userDetail.setCreatedDate(new Date());

            userDetailJpaRepository.save(userDetail);

            UserPasswordEntity userPassword = new UserPasswordEntity();
            userPassword.setUserDetail(userDetail);
            userPassword.setPassword(Util.hashPassword(request.getPassword()));
            userPassword.setCreatedBy("system");
            userPassword.setCreatedDate(new Date());
            userPassword.setExpired(DateUtil.expiredDate(new Date()));

            userPasswordJpaRepository.save(userPassword);

            response = BaseResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Success")
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            response = BaseResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
        }

        return response;
    }

    @Override
    public BaseResponse<Object> login(LoginRequest request) {
        BaseResponse<Object> response;

        try {
            var user = Optional.of(userDetailJpaRepository.findByEmail(request.getEmail()))
                    .orElseThrow(
                            () -> new UnauthorizedException("User not found")
                    );

            var password = Optional.of(userPasswordJpaRepository.findByUserDetail(user.get()))
                    .orElseThrow(
                            () -> new UnauthorizedException("Wrong password")
                    );

            if (password.isPresent() &&
                    !password.get().getPassword().equals(Util.hashPassword(request.getPassword()))) {
                throw new UnauthorizedException("Wrong password");
            }

            var token = jwtUtil.generateToken(user.get().getEmail());

            response = BaseResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Success")
                    .data(token)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
        return response;
    }
}
