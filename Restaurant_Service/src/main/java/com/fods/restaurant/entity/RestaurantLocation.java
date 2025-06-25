package com.fods.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "restaurant_locations", indexes = {
        @Index(name = "idx_lat_lon", columnList = "latitude, longitude")
})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private double latitude;

    private double longitude;
}

