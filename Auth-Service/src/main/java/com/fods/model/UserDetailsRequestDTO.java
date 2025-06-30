package com.fods.model;

public record UserDetailsRequestDTO(
        String userName,
        String contactNumber,
        String emailId,
        String password,
        UserAddressDTO addressDetails
) {

}

