package com.fods.restaurant.model;

import java.util.List;

public record RestaurantFoodMenuDTO(
        long restaurantId,
        String name,
        List<MenuItemDTO> menuItemList
) {
}
