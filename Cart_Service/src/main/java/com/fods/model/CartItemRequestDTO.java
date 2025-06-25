package com.fods.model;

import java.util.UUID;

public record CartItemRequestDTO(
        long restaurantId,
        UUID userId,
        long menuItemId,
        int quantity
) {
}
