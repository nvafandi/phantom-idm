package com.phantom.idm.service;

import com.phantom.idm.dto.request.LoginRequest;
import com.phantom.idm.dto.request.RegisterRequest;
import com.phantom.idm.dto.response.BaseResponse;

public interface AuthService {

    BaseResponse<Object> register(RegisterRequest request);

    BaseResponse<Object> login(LoginRequest request);

}
