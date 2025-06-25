package com.fods.restaurant.service;

import com.fods.restaurant.model.MenuItemDetailResponseDTO;
import com.fods.restaurant.model.MenuItemNameAndPriceDTO;
import com.fods.restaurant.model.RestaurantFoodMenuDTO;

import java.math.BigDecimal;

public interface RestaurantService {
    RestaurantFoodMenuDTO getFoodMenu(long restaurantId, String restaurantName);
    MenuItemDetailResponseDTO<BigDecimal> getItemPrice(long menuId, long restaurantId);
    MenuItemDetailResponseDTO<String> getItemName(long menuId, long restaurantId);
    MenuItemDetailResponseDTO<MenuItemNameAndPriceDTO> getItemDetails(long menuId, long restaurantId);
}
