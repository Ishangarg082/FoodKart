package com.fods.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestaurantDetailsResponseDTO {
    private long restaurantId;
    private String restaurantName;
    private String restaurantAddress;
}
