package com.fods.helper;

import com.fods.model.CartItemRequestDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartValidator {
    public void validateAddRequest(CartItemRequestDTO request) {
        validateUserId(request.userId());
        validateMenuId(request.menuItemId());
        validateQuantity(request.quantity());
    }

    public void validateUpdateRequest(UUID userId, long menuId, int quantity) {
        validateUserId(userId);
        validateMenuId(menuId);
        validateQuantity(quantity);
    }

    public void validateDeleteRequest(UUID userId, long menuId) {
        validateUserId(userId);
        validateMenuId(menuId);
    }

    public void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
    }

    public void validateMenuId(long menuId) {
        if (menuId <= 0) {
            throw new IllegalArgumentException("Menu ID must be greater than 0");
        }
    }

    public void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}
