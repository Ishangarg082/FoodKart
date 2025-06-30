package com.fods.model;

import java.util.UUID;

public record UserDetailResponseDTO(
        UUID userId,
        String userName,
        String phoneNumber
) {
}
