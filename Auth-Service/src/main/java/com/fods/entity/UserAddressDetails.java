package com.fods.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_address_details", indexes = {
        @Index(name = "idx_city", columnList = "city"),
        @Index(name = "idx_state", columnList = "state"),
        @Index(name = "idx_pinCode", columnList = "pinCode"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserAddressDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID addressId;

    private String street;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    @Column(name = "building_name")
    private String buildingName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails userDetails;

}
