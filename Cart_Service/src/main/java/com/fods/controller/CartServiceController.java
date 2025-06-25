package com.fods.controller;

import com.fods.model.CartItemRequestDTO;
import com.fods.model.CartItemResponseDTO;
import com.fods.service.CartService;
import com.fods.service.impl.CartServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/cart")
public class CartServiceController {
    private final CartService cartService;

    public CartServiceController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addItemInCart(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return Optional.ofNullable(cartService.addItemToCart(cartItemRequestDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());

    }

    @PatchMapping("/update")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(@RequestParam UUID userId,
                                                              @RequestParam long menuId,
                                                              @RequestParam int quantity) {
        return Optional
                .ofNullable(cartService.updateItemToCart(userId, menuId, quantity))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CartItemResponseDTO> deleteItemToCart(@RequestParam UUID userId, @RequestParam long menuId) {
        return Optional.ofNullable(cartService.deleteItemToCart(userId, menuId)).map(ResponseEntity::ok).orElse(
                ResponseEntity.noContent().build());
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestParam UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart Cleared Successfully");
    }

    @PatchMapping("/save")
    public ResponseEntity<String> saveForLater(@RequestParam UUID userId) {
        cartService.saveForLater(userId);
        return ResponseEntity.ok("Cart saved Successfully");
    }

    @PatchMapping("/order_placed/{userId}")
    public ResponseEntity<Void> orderPlaced(@PathVariable("userId") UUID userId) {
        cartService.updateCartDetail(userId);
        return ResponseEntity.ok().build();
    }

}
