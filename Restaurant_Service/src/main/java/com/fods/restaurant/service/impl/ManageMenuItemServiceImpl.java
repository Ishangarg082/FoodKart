package com.fods.restaurant.service.impl;

import com.fods.restaurant.entity.MenuItem;
import com.fods.restaurant.entity.Restaurant;
import com.fods.restaurant.exception.EntityNotFoundException;
import com.fods.restaurant.mapper.RestaurantServiceMapper;
import com.fods.restaurant.model.MenuItemRequestDTO;
import com.fods.restaurant.model.MenuItemResponseDTO;
import com.fods.restaurant.model.RestaurantDetailsResponseDTO;
import com.fods.restaurant.repository.MenuItemRepository;
import com.fods.restaurant.repository.RestaurantRepository;
import com.fods.restaurant.service.ManageMenuItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManageMenuItemServiceImpl implements ManageMenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public ManageMenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantDetailsResponseDTO getRestaurantDetails(long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(RestaurantServiceMapper::toRestaurantDetailsResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + restaurantId));
    }

    @Override
    @Transactional
    public MenuItemResponseDTO addMenuItem(long restaurantId, MenuItemRequestDTO menuItemRequestDTO) {
        MenuItem menuItem = RestaurantServiceMapper.toMenuItem(menuItemRequestDTO);
        menuItem.setRestaurant(Restaurant.builder().id(restaurantId).build());
        MenuItem save = menuItemRepository.save(menuItem);
        return RestaurantServiceMapper.toMenuItemResponseDTO(save);
    }

    @Override
    @Transactional
    public MenuItemResponseDTO updateMenuItem(long menuId, MenuItemRequestDTO menuItemRequestDTO) {
        MenuItem menuItem = menuItemRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException(
                "Menu Item not found!!!!"));
        menuItem.setId(menuId);
        menuItem.setName(menuItemRequestDTO.name());
        menuItem.setCategory(menuItemRequestDTO.category());
        menuItem.setDescription(menuItemRequestDTO.description());
        menuItem.setPrice(menuItemRequestDTO.price());
        menuItem.setIsAvailable(menuItemRequestDTO.isAvailable());
        menuItem.setImageUrl(menuItem.getImageUrl());

        MenuItem save = menuItemRepository.save(menuItem);
        return RestaurantServiceMapper.toMenuItemResponseDTO(save);
    }

    @Override
    @Transactional
    public MenuItemResponseDTO updateFoodAvailability(long itemId, boolean isAvailable) {
        MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException(
                "Menu Item not found!!!!"));
        menuItem.setIsAvailable(isAvailable);
        MenuItem menuItem1 = menuItemRepository.save(menuItem);
        return RestaurantServiceMapper.toMenuItemResponseDTO(menuItem1);
    }

    @Override
    @Transactional
    public void deleteMenuItem(long menuId) {
        if (!menuItemRepository.existsById(menuId)) {
            throw new EntityNotFoundException("Menu Item not found!!!!");
        }
        menuItemRepository.deleteById(menuId);
    }
}
