package com.fods.restaurant.mapper;

import com.fods.restaurant.entity.MenuItem;
import com.fods.restaurant.entity.Restaurant;
import com.fods.restaurant.entity.RestaurantLocation;
import com.fods.restaurant.model.*;

import java.util.List;

public class RestaurantServiceMapper {
    public static RestaurantFoodMenuDTO toRestaurantFoodMenuDTO(long restaurantId,
                                                                String restaurantName,
                                                                List<MenuItem> menuItems) {
        List<MenuItemDTO> menuItemDTOS = menuItems
                .stream()
                .map(RestaurantServiceMapper::toMenuItemDTO)
                .toList();

        return new RestaurantFoodMenuDTO(restaurantId, restaurantName, menuItemDTOS);
    }

    public static MenuItemDTO toMenuItemDTO(MenuItem menuItem) {
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.getCategory(),
                menuItem.getImageUrl()
        );
    }

    public static MenuItemResponseDTO toMenuItemResponseDTO(MenuItem menuItem) {
        return new MenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getCategory(),
                menuItem.getPrice(),
                menuItem.getIsAvailable(),
                menuItem.getImageUrl(),
                menuItem.getRestaurant().getId()
        );
    }

    public static MenuItem toMenuItem(MenuItemRequestDTO menuItemRequestDTO) {
        return MenuItem
                .builder()
                .name(menuItemRequestDTO.name())
                .name(menuItemRequestDTO.name())
                .description(menuItemRequestDTO.description())
                .category(menuItemRequestDTO.category())
                .price(menuItemRequestDTO.price())
                .isAvailable(menuItemRequestDTO.isAvailable())
                .imageUrl(menuItemRequestDTO.imageUrl())
                .build();
    }

    public static RestaurantDetailsResponseDTO toRestaurantDetailsResponseDTO(Restaurant restaurant) {
        return new RestaurantDetailsResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                getRestaurantAddress(restaurant.getLocation())
        );
    }

    public static String getRestaurantAddress(RestaurantLocation restaurantLocation) {
        return restaurantLocation.getCity() + ", " + restaurantLocation.getState();
    }

    public static <T> MenuItemDetailResponseDTO<T> tMenuItemDetailResponseDTO(long menuId, T data) {
        return new MenuItemDetailResponseDTO<>(menuId, data);
    }
}
