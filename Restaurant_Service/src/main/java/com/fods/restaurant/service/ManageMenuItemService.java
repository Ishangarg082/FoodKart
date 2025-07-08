package com.fods.restaurant.service;

import com.fods.restaurant.model.MenuItemRequestDTO;
import com.fods.restaurant.model.MenuItemResponseDTO;
import com.fods.restaurant.model.RestaurantDetailsResponseDTO;

public interface ManageMenuItemService {
    MenuItemResponseDTO updateFoodAvailability(long itemId, boolean isAvailable);
    MenuItemResponseDTO addMenuItem(long restaurantId, MenuItemRequestDTO menuItemRequestDTO);
    void deleteMenuItem(long menuId);
    MenuItemResponseDTO updateMenuItem(long menuId, MenuItemRequestDTO menuItemRequestDTO);
    RestaurantDetailsResponseDTO getRestaurantDetails(long restaurantId);
}
