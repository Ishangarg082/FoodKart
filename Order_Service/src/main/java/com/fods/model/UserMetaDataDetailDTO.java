package com.fods.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMetaDataDetailDTO {
    private String userName;
    private String address;
    private String contactNumber;
}
