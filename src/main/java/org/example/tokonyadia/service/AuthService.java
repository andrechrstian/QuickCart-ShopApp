package org.example.tokonyadia.service;

import org.example.tokonyadia.dto.request.AuthRequest;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.LoginResponse;
import org.example.tokonyadia.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer (AuthRequest<CustomerRequest> authRequest);
    RegisterResponse registerAdmin (AuthRequest<CustomerRequest> authRequest);
    LoginResponse login (AuthRequest<String> request);
}
