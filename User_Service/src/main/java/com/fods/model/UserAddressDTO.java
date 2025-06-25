package com.fods.model;

public record UserAddressDTO(
        String state,
        String city,
        int pinCode,
        String street,
        String buildingName
) {
}
