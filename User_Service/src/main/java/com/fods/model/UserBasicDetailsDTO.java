package com.fods.model;

public record UserBasicDetailsDTO(
        String userName,
        String contactNumber,
        String emailId,
        String password
) {
}
