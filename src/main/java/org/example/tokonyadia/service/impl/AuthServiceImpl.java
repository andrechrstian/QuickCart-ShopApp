package org.example.tokonyadia.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tokonyadia.dto.request.AuthRequest;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.LoginResponse;
import org.example.tokonyadia.dto.response.RegisterResponse;
import org.example.tokonyadia.entity.AppUser;
import org.example.tokonyadia.entity.Role;
import org.example.tokonyadia.entity.User;
import org.example.tokonyadia.repository.UserRepository;
import org.example.tokonyadia.security.JwtUtil;
import org.example.tokonyadia.service.AuthService;
import org.example.tokonyadia.service.CustomerService;
import org.example.tokonyadia.service.RoleService;
import org.example.tokonyadia.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleService roleService;
    private final CustomerService customerService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public RegisterResponse registerAdmin(AuthRequest<CustomerRequest> authRequest) {
        Role role = roleService.getOrSave(Role.ERole.ADMIN);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .username(authRequest.getUsername().toLowerCase())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .roles(roles)
                .build();

        user = userRepository.save(user);

        CustomerRequest requestData = authRequest.getData().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Not Found")
        );

        requestData.setUser(user);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .role(role.getName())
                .build();
    }

    @Override
    @Transactional
    public RegisterResponse registerCustomer(AuthRequest<CustomerRequest> request) {
        Role role = roleService.getOrSave(Role.ERole.CUSTOMER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .username(request.getUsername().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        user = userRepository.save(user);

        CustomerRequest requestData = request.getData().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found")
        );

        requestData.setUser(user); //set relation customer to user

        customerService.saveCustomer(requestData);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .role(role.getName())
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest<String> request) {

        //Authentication Manager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();

        //TODO : Generate Token
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole())
                .build();
    }
}
