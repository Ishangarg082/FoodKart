package com.fods.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserAddressDetailDTO {
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String buildingName;
}
