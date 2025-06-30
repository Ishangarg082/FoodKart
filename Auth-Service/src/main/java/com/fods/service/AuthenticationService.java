package com.fods.service;

import com.fods.helper.AuthServiceHelper;
import com.fods.model.UserDetailResponseDTO;
import com.fods.model.UserDetailsRequestDTO;
import com.fods.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserDetailsRepository userDetailsRepository;
    private final JwtService jwtService;
    private final AuthServiceHelper authServiceHelper;

    public AuthenticationService(UserDetailsRepository userDetailsRepository,
                                 JwtService jwtService,
                                 AuthServiceHelper authServiceHelper) {
        this.userDetailsRepository = userDetailsRepository;
        this.jwtService = jwtService;
        this.authServiceHelper = authServiceHelper;
    }

    public UserDetailResponseDTO saveUser(UserDetailsRequestDTO userDetail) {
        // Call UserDetailsServiceImpl, saveUserDetails() method using inter-microservice communication
        return authServiceHelper
                .saveUserDetails(userDetail)
                .orElseThrow(() -> new RuntimeException("Not able to save user"));
    }

    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public Map<String, String> getDetailsFromToken(String token) {
        return jwtService.extractUserNameAndRole(token);
    }
}
