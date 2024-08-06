package org.example.tokonyadia.service;

import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.LoginResponse;
import org.example.tokonyadia.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer (CustomerRequest.AuthRequest<CustomerRequest> authRequest);
    RegisterResponse registerAdmin (CustomerRequest.AuthRequest<CustomerRequest> authRequest);
    LoginResponse login (CustomerRequest.AuthRequest<String> request);
}
