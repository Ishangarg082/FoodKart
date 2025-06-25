package com.fods.restaurant.model;

import java.math.BigDecimal;

public record MenuItemDTO(
        long menuId,
        String menuName,
        BigDecimal price,
        String description,
        String category,
        String imageURL
) {
}
