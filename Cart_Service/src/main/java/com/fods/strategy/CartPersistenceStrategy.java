package com.fods.strategy;

import com.fods.model.CartItemResponseDTO;

import java.util.UUID;

public interface CartPersistenceStrategy {
    void persistCart(UUID userId, CartItemResponseDTO cartItem);
}
