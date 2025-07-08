package com.fods.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDetailsResponseDTO {
    private long restaurantId;
    private String restaurantName;
    private String restaurantAddress;
}
