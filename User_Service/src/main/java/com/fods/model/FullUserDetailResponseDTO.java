package com.fods.model;

import java.util.UUID;

public record FullUserDetailResponseDTO(
        UUID userId,
        String userName,
        String contactNumber,
        String email,
        UserAddressDTO userAddressDTO
) {
}
