package com.phantom.idm.controller;

import com.phantom.idm.dto.request.LoginRequest;
import com.phantom.idm.dto.request.RegisterRequest;
import com.phantom.idm.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/v1/register")
    ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        var response = authService.register(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/v1/login")
    ResponseEntity<Object> login(@RequestBody LoginRequest request) {

        var response = authService.login(request);

        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
}
