package com.fods.restaurant.controller;

import com.fods.restaurant.model.MenuItemDetailResponseDTO;
import com.fods.restaurant.model.MenuItemNameAndPriceDTO;
import com.fods.restaurant.model.MenuItemResponseDTO;
import com.fods.restaurant.model.RestaurantFoodMenuDTO;
import com.fods.restaurant.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/v1/restaurant/menu/{restaurantId}")
public class GetFoodMenuController {

    private final RestaurantService restaurantService;

    public GetFoodMenuController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/menu")
    public ResponseEntity<RestaurantFoodMenuDTO> getFoodMenu(
            @PathVariable(name = "restaurantId") long restaurantId,
            @RequestParam(name = "restaurantName") String restaurantName
    ) {

        return Optional.ofNullable(
                        restaurantService.getFoodMenu(restaurantId, restaurantName))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());

        /*RestaurantFoodMenuDTO foodMenu = restaurantService.getFoodMenu(restaurantId, restaurantName);
        if (foodMenu != null) {
            return ResponseEntity.ok(foodMenu);
        }
        return ResponseEntity.noContent().build();*/
    }

    @GetMapping("/menu/{menuId}/name")
    public ResponseEntity<MenuItemDetailResponseDTO<String>> getItemName(@PathVariable(name = "restaurantId") long restaurantId,
                                                                         @PathVariable(name = "menuId") long menuId) {
        /*MenuItemDetailResponseDTO<String> itemName = restaurantService.getItemName(menuId, restaurantId);
        if (itemName != null)
            return ResponseEntity.ok(itemName);
        return ResponseEntity.noContent().build();*/

        return Optional.ofNullable(restaurantService.getItemName(menuId, restaurantId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/menu/{menuId}/price")
    public ResponseEntity<MenuItemDetailResponseDTO<BigDecimal>> getItemPrice(@PathVariable(name = "restaurantId") long restaurantId,
                                                                              @PathVariable(name = "menuId") long menuId) {
        /*MenuItemDetailResponseDTO<BigDecimal> itemName = restaurantService.getItemPrice(menuId, restaurantId);
        if (itemName != null)
            return ResponseEntity.ok(itemName);
        return ResponseEntity.noContent().build();*/

        return Optional.ofNullable(restaurantService.getItemPrice(menuId, restaurantId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());

    }

    @GetMapping("/menu/{menuId}/detail")
    public ResponseEntity<MenuItemDetailResponseDTO<MenuItemNameAndPriceDTO>> getItemDetail(@PathVariable(name = "restaurantId") long restaurantId,
                                                                                            @PathVariable(name = "menuId") long menuId) {
        return Optional.ofNullable(restaurantService.getItemDetails(menuId, restaurantId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
