package com.fods.controller;

import com.fods.model.UserDetailResponseDTO;
import com.fods.model.UserDetailsRequestDTO;
import com.fods.service.UserDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users/")
public class UserDetailsServiceController {
    private final UserDetailService userDetailService;
    private static final Logger logger = LogManager.getLogger(UserDetailsServiceController.class);

    public UserDetailsServiceController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailResponseDTO> registerUserDetails(@RequestBody UserDetailsRequestDTO userDetailsRequestDTO) {
        logger.info("Received request to register user details: {}", userDetailsRequestDTO.toString());
        System.out.println(userDetailsRequestDTO.toString());
        UserDetailResponseDTO userDetailResponseDTO = userDetailService.saveUserDetails(userDetailsRequestDTO);
        if (userDetailResponseDTO != null) {
            return ResponseEntity.ok(userDetailResponseDTO);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update")
    public ResponseEntity<UserDetailResponseDTO> updateExistingUserDetail(@RequestBody UserDetailsRequestDTO userDetailsRequestDTO) {
        UserDetailResponseDTO userDetailResponseDTO = userDetailService.updateUserDetails(userDetailsRequestDTO);
        if (userDetailResponseDTO != null) {
            return ResponseEntity.ok(userDetailResponseDTO);
        }
        return ResponseEntity.noContent().build();
    }

    // Login-Controller

    // Logout-Controller
}
