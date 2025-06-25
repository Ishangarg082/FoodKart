package com.fods.mapper;

import com.fods.entity.CartDetail;
import com.fods.entity.CartItemDetail;
import com.fods.model.CartItemRequestDTO;
import com.fods.model.CartItemResponseDTO;
import com.fods.model.CartItems;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class CartServiceMapper {
    public static CartItemResponseDTO initializeNewCart(CartItemRequestDTO cartItemRequestDTO) {
        return new CartItemResponseDTO(UUID.randomUUID(), cartItemRequestDTO.userId(),
                cartItemRequestDTO.restaurantId(),
                new ArrayList<>(),
                null,
                String.valueOf(System.currentTimeMillis() / 1000L));
    }

    public static CartItemDetail toCartItemDetail(CartItems cartItems, long restaurantId) {
        return CartItemDetail.builder()
                .menuItemId(cartItems.getMenuItemId())
                .quantity(cartItems.getQuantity())
                .restaurantId(restaurantId)
                .price(cartItems.getPrice())
                .build();
    }

    public static CartDetail toCartDetail(CartItemResponseDTO itemResponseDTO) {
        return CartDetail.builder()
                .userId(itemResponseDTO.getUserId()) // fix if needed (either make it UUID or Long everywhere)
                .lastUpdated(LocalDateTime.now())
                .totalPrice(itemResponseDTO.getTotalPrice())
                .build();
    }
}
