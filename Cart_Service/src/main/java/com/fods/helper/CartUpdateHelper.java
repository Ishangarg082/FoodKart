package com.fods.helper;

import com.fods.exception.ItemNotFoundException;
import com.fods.model.CartItemRequestDTO;
import com.fods.model.CartItemResponseDTO;
import com.fods.model.CartItems;
import com.fods.model.MenuItemNameAndPriceDTO;
import com.fods.utility.CartServiceUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class CartUpdateHelper {
    public void addOrUpdateCartItem(CartItemResponseDTO cart, CartItemRequestDTO request, MenuItemNameAndPriceDTO itemDetails) {
        List<CartItems> items = cart.getCartItemsList();

        items.stream()
                .filter(item -> item.getMenuItemId() == request.menuItemId())
                .findFirst()
                .ifPresentOrElse(existing -> {
                    existing.setQuantity(request.quantity());
                    existing.setItemName(itemDetails.name());
                    existing.setPrice(CartServiceUtils.calculateItemTotal(request.quantity(), itemDetails.price()));
                }, () -> {
                    items.add(new CartItems(
                            request.menuItemId(),
                            itemDetails.name(),
                            request.quantity(),
                            CartServiceUtils.calculateItemTotal(request.quantity(), itemDetails.price())
                    ));
                });
    }

    public void updateItemQuantity(CartItemResponseDTO cart, long menuId, int newQuantity) {
        CartItems item = findItemOrThrow(cart.getCartItemsList(), menuId);
        BigDecimal unitPrice = item.getPrice()
                .divide(BigDecimal.valueOf(item.getQuantity()), RoundingMode.HALF_UP);
        item.setQuantity(newQuantity);
        item.setPrice(CartServiceUtils.calculateItemTotal(newQuantity, unitPrice));
    }

    public void removeItemFromCart(CartItemResponseDTO cart, long menuId) {
        boolean removed = cart.getCartItemsList().removeIf(item -> item.getMenuItemId() == menuId);
        if (!removed) {
            throw new ItemNotFoundException(String.format("Item with menuId %d not found in cart", menuId));
        }
    }

    private CartItems findItemOrThrow(List<CartItems> items, long menuId) {
        return items.stream()
                .filter(item -> item.getMenuItemId() == menuId)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException(
                        String.format("Item with menuId %d not found in cart", menuId)));
    }
}
