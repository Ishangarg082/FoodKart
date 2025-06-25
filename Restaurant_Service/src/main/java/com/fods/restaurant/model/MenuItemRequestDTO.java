package com.fods.restaurant.model;

import java.math.BigDecimal;

public record MenuItemRequestDTO(
        String name,
        String description,
        String category,
        BigDecimal price,
        boolean isAvailable,
        String imageUrl
) {
}
