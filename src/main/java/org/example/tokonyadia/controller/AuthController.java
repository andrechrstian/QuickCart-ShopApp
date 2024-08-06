package org.example.tokonyadia.controller;

import lombok.RequiredArgsConstructor;
import org.example.tokonyadia.constant.APIUrl;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.CommonResponse;
import org.example.tokonyadia.dto.response.LoginResponse;
import org.example.tokonyadia.dto.response.RegisterResponse;
import org.example.tokonyadia.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(APIUrl.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerCustomer(@RequestBody CustomerRequest.AuthRequest<CustomerRequest> request) {

        RegisterResponse response = authService.registerCustomer(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Succesfully register new customer")
                .data(Optional.of(response))
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerAdmin(@RequestBody CustomerRequest.AuthRequest<CustomerRequest> request) {

        RegisterResponse response = authService.registerAdmin(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Succesfully register new admin")
                .data(Optional.of(response))
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> loginCustomer(@RequestBody CustomerRequest.AuthRequest<String> request) {
        LoginResponse response = authService.login(request);
        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Login Success")
                .data(Optional.of(response))
                .build();

        return ResponseEntity.ok(commonResponse);

    }

}
