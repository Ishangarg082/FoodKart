package com.fods.restaurant.controller;

import com.fods.restaurant.model.MenuItemRequestDTO;
import com.fods.restaurant.model.MenuItemResponseDTO;
import com.fods.restaurant.model.RestaurantDetailsResponseDTO;
import com.fods.restaurant.service.ManageMenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurant/owner/{restaurantId}")
public class RestaurantFoodMenuManagementController {
    private final ManageMenuItemService manageMenuItemService;

    public RestaurantFoodMenuManagementController(ManageMenuItemService manageMenuItemService) {
        this.manageMenuItemService = manageMenuItemService;
    }

    @GetMapping("/restaurant-details")
    public ResponseEntity<RestaurantDetailsResponseDTO> getRestaurantDetails(@PathVariable(name = "restaurantId") long restaurantId) {
        RestaurantDetailsResponseDTO restaurantDetails = manageMenuItemService.getRestaurantDetails(restaurantId);
        if (restaurantDetails != null) {
            return ResponseEntity.ok(restaurantDetails);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add_menu")
    public ResponseEntity<MenuItemResponseDTO> addMenuItemController(@PathVariable(name = "restaurantId") long restaurantId,
                                                                     @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        MenuItemResponseDTO menuItemResponseDTO = manageMenuItemService.addMenuItem(restaurantId, menuItemRequestDTO);
        if (menuItemResponseDTO != null) {
            return ResponseEntity.ok(menuItemResponseDTO);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-menu-item/{itemId}")
    public ResponseEntity<MenuItemResponseDTO> updateExistingMenu(
            @PathVariable(name = "itemId") long itemId,
            @RequestBody MenuItemRequestDTO menuItemRequestDTO
    ) {
        MenuItemResponseDTO menuItemResponseDTO = manageMenuItemService.updateMenuItem(itemId, menuItemRequestDTO);
        if (menuItemResponseDTO != null) return ResponseEntity.ok(menuItemResponseDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/menu_item/{itemId}/stock")
    public ResponseEntity<MenuItemResponseDTO> updateFoodItem(
            @PathVariable(name = "itemId") long itemId,
            @RequestParam boolean isAvailable
    ) {
        MenuItemResponseDTO menuItemResponseDTO = manageMenuItemService.updateFoodAvailability(itemId, isAvailable);
        return ResponseEntity.ok(menuItemResponseDTO);
    }

    @DeleteMapping("/delete-food-item/{itemId}")
    public ResponseEntity<Void> deleteExistingFoodItem(@PathVariable(name = "itemId") long itemId) {
        manageMenuItemService.deleteMenuItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
