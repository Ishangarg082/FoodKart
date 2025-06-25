package com.fods.restaurant.service.impl;

import com.fods.restaurant.entity.MenuItem;
import com.fods.restaurant.exception.FoodItemNotPresentException;
import com.fods.restaurant.exception.InvalidRestaurantDetailsException;
import com.fods.restaurant.exception.MenuIdCannotByBlankException;
import com.fods.restaurant.mapper.RestaurantServiceMapper;
import com.fods.restaurant.model.MenuItemDetailResponseDTO;
import com.fods.restaurant.model.MenuItemNameAndPriceDTO;
import com.fods.restaurant.model.RestaurantFoodMenuDTO;
import com.fods.restaurant.repository.MenuItemRepository;
import com.fods.restaurant.service.RestaurantService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Logger logger = LogManager.getLogger(RestaurantServiceImpl.class);
    private final MenuItemRepository menuItemRepository;

    public RestaurantServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public RestaurantFoodMenuDTO getFoodMenu(long restaurantId, String restaurantName) {
        logger.info("Fetching full menu for restaurantId: {}, restaurantName: {}", restaurantId, restaurantName);

        if (restaurantId == 0 || restaurantName == null || restaurantName.trim().isEmpty()) {
            throw new InvalidRestaurantDetailsException("Restaurant ID and name must not be empty.");
        }

        List<MenuItem> menuItems = menuItemRepository
                .findByRestaurantIdAndRestaurantNameAndIsAvailableIsTrue(restaurantId, restaurantName)
                .orElseThrow(() -> new FoodItemNotPresentException(
                        "No food items found for the provided restaurant ID and name"));

        return RestaurantServiceMapper.toRestaurantFoodMenuDTO(restaurantId, restaurantName, menuItems);
    }

    @Override
    public MenuItemDetailResponseDTO<BigDecimal> getItemPrice(long menuId, long restaurantId) {
        logger.info("Fetching item price for menuId: {}, restaurantId: {}", menuId, restaurantId);
        validateIds(menuId, restaurantId);

        BigDecimal price = menuItemRepository.findPriceByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new FoodItemNotPresentException(
                        "No food item found for the provided restaurant and menu ID"));

        return RestaurantServiceMapper.tMenuItemDetailResponseDTO(menuId, price);
    }

    @Override
    public MenuItemDetailResponseDTO<String> getItemName(long menuId, long restaurantId) {
        logger.info("Fetching item name for menuId: {}, restaurantId: {}", menuId, restaurantId);
        validateIds(menuId, restaurantId);

        String itemName = menuItemRepository.findNameByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new FoodItemNotPresentException(
                        "No food item found for the provided restaurant and menu ID"));

        return RestaurantServiceMapper.tMenuItemDetailResponseDTO(menuId, itemName);
    }

    @Override
    public MenuItemDetailResponseDTO<MenuItemNameAndPriceDTO> getItemDetails(long menuId, long restaurantId) {
        logger.info("Fetching item details for menuId: {}, restaurantId: {}", menuId, restaurantId);
        validateIds(menuId, restaurantId);

        MenuItemNameAndPriceDTO itemDetails = menuItemRepository.findNameAndPriceByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new FoodItemNotPresentException(
                        "No food item found for the provided restaurant and menu ID"));

        return RestaurantServiceMapper.tMenuItemDetailResponseDTO(menuId, itemDetails);
    }

    // Utility method to validate IDs
    private void validateIds(long menuId, long restaurantId) {
        if (menuId <= 0 || restaurantId <= 0) {
            throw new MenuIdCannotByBlankException("Menu ID and Restaurant ID must be greater than zero.");
        }
    }
}
