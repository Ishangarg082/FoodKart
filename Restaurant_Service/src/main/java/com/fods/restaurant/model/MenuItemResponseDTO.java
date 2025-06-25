package com.fods.restaurant.model;

import java.math.BigDecimal;

public record MenuItemResponseDTO(
        long menuId,
        String name,
        String description,
        String category,
        BigDecimal price,
        boolean isAvailable,
        String imageUrl,
        long restaurantId
) {
}
