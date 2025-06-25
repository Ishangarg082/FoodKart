package com.fods.controller;

import com.fods.model.UserDetailResponseDTO;
import com.fods.model.UserDetailsRequestDTO;
import com.fods.service.UserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users/")
public class UserDetailsServiceController {
    private final UserDetailService userDetailService;

    public UserDetailsServiceController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping("/register_new_user")
    public ResponseEntity<UserDetailResponseDTO> registerUserDetails(@RequestBody UserDetailsRequestDTO userDetailsRequestDTO) {
        UserDetailResponseDTO userDetailResponseDTO = userDetailService.saveUserDetails(userDetailsRequestDTO);
        if(userDetailResponseDTO != null) {
            return ResponseEntity.ok(userDetailResponseDTO);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update_user_detail")
    public ResponseEntity<UserDetailResponseDTO> updateExistingUserDetail(@RequestBody UserDetailsRequestDTO userDetailsRequestDTO) {
        UserDetailResponseDTO userDetailResponseDTO = userDetailService.updateUserDetails(userDetailsRequestDTO);
        if(userDetailResponseDTO != null) {
            return ResponseEntity.ok(userDetailResponseDTO);
        }
        return ResponseEntity.noContent().build();
    }

    // Login-Controller

    // Logout-Controller
}
