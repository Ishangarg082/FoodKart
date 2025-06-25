package com.fods.service;

import com.fods.model.UserDetailResponseDTO;
import com.fods.model.UserDetailsRequestDTO;

public interface UserDetailService {
    UserDetailResponseDTO saveUserDetails(UserDetailsRequestDTO userDetailsRequestDTO);
    UserDetailResponseDTO updateUserDetails(UserDetailsRequestDTO userDetailsRequestDTO);
}
