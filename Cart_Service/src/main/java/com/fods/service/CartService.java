package com.fods.service;

import com.fods.model.CartItemRequestDTO;
import com.fods.model.CartItemResponseDTO;

import java.util.UUID;

public interface CartService {
    CartItemResponseDTO addItemToCart(CartItemRequestDTO cartItemRequestDTO);
    CartItemResponseDTO updateItemToCart(UUID userId, long menuId, int quantity);
    void clearCart(UUID userId);
    CartItemResponseDTO deleteItemToCart(UUID userId, long menuId);
    void saveForLater(UUID userId);
    void updateCartDetail(UUID userId);
}
