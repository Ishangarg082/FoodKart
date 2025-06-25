package com.fods.service.impl;

import com.fods.client.RestaurantServiceClient;
import com.fods.exception.CartEmptyException;
import com.fods.helper.CartUpdateHelper;
import com.fods.helper.CartValidator;
import com.fods.mapper.CartServiceMapper;
import com.fods.model.CartItemRequestDTO;
import com.fods.model.CartItemResponseDTO;
import com.fods.model.MenuItemNameAndPriceDTO;
import com.fods.model.enums.CartStatus;
import com.fods.repository.CartDetailRepository;
import com.fods.repository.CustomRedisCache;
import com.fods.service.CartService;
import com.fods.utility.CartServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = LogManager.getLogger(CartServiceImpl.class);

    private final RestaurantServiceClient restaurantServiceClient;
    private final CustomRedisCache customRedisCache;
    private final CartValidator cartValidator;
    private final CartUpdateHelper cartUpdateHelper;
    private final CartDetailRepository cartDetailRepository;


    public CartServiceImpl(RestaurantServiceClient restaurantServiceClient,
                           CustomRedisCache customRedisCache,
                           CartValidator cartValidator,
                           CartUpdateHelper cartUpdateHelper,
                           CartDetailRepository cartDetailRepository) {
        this.restaurantServiceClient = restaurantServiceClient;
        this.customRedisCache = customRedisCache;
        this.cartValidator = cartValidator;
        this.cartUpdateHelper = cartUpdateHelper;
        this.cartDetailRepository = cartDetailRepository;
    }

    @Override
    public CartItemResponseDTO addItemToCart(CartItemRequestDTO requestDTO) {
        cartValidator.validateAddRequest(requestDTO);

        CartItemResponseDTO cart = customRedisCache
                .getItemFromCache(requestDTO.userId(), CartItemResponseDTO.class)
                .filter(existing -> existing.getRestaurantId() == requestDTO.restaurantId())
                .orElseGet(() -> CartServiceMapper.initializeNewCart(requestDTO));

        MenuItemNameAndPriceDTO itemDetails = restaurantServiceClient.fetchItemDetail(
                requestDTO.menuItemId(), requestDTO.restaurantId());

        cartUpdateHelper.addOrUpdateCartItem(cart, requestDTO, itemDetails);
        cart.setTotalPrice(CartServiceUtils.calculateCartTotal(cart.getCartItemsList()));

        customRedisCache.saveItemInCache(requestDTO.userId(), cart);
        logger.info("Cart updated for userId {}: {}", requestDTO.userId(), cart);
        return cart;
    }

    @Override
    public CartItemResponseDTO updateItemToCart(UUID userId, long menuId, int quantity) {
        cartValidator.validateUpdateRequest(userId, menuId, quantity);

        CartItemResponseDTO cart = customRedisCache
                .getItemFromCache(userId, CartItemResponseDTO.class)
                .orElseThrow(() -> new CartEmptyException("No item present in cart for user %d", userId));

        cartUpdateHelper.updateItemQuantity(cart, menuId, quantity);
        customRedisCache.saveItemInCache(userId, cart);

        logger.info("Updated item quantity in cart for userId: {}", userId);

        return cart;
    }

    @Override
    public CartItemResponseDTO deleteItemToCart(UUID userId, long menuId) {
        cartValidator.validateDeleteRequest(userId, menuId);

        CartItemResponseDTO cart = customRedisCache
                .getItemFromCache(userId, CartItemResponseDTO.class)
                .orElseThrow(() -> new CartEmptyException("No item present in cart for user %d", userId));

        cartUpdateHelper.removeItemFromCart(cart, menuId);

        if (cart.getCartItemsList().isEmpty()) {
            customRedisCache.deleteItemInCache(userId);
            logger.info("Cart cleared for userId: {}", userId);
        } else {
            customRedisCache.saveItemInCache(userId, cart);
            logger.info("Item removed from cart for userId: {}", userId);
        }

        return cart;
    }

    @Override
    public void clearCart(UUID userId) {
        cartValidator.validateUserId(userId);
        customRedisCache.deleteItemInCache(userId);

        cartDetailRepository.findByUserId(userId).ifPresent(userCart -> {
            userCart.setStatus(CartStatus.CANCELLED);
            userCart.setLastUpdated(LocalDateTime.now());
            cartDetailRepository.save(userCart);
            logger.info("Cart status set to CANCELLED for userId: {}", userId);
        });
    }

    @Override
    public void saveForLater(UUID userId) {
        cartValidator.validateUserId(userId);
        customRedisCache.deleteItemInCache(userId);

        cartDetailRepository.findByUserId(userId).ifPresent(userCart -> {
            userCart.setStatus(CartStatus.SAVED);
            userCart.setLastUpdated(LocalDateTime.now());
            cartDetailRepository.save(userCart);
            logger.info("Cart status set to SAVED for userId: {}", userId);
        });
    }

    @Override
    public void updateCartDetail(UUID userId) {
        cartValidator.validateUserId(userId);

        cartDetailRepository.findByUserId(userId).ifPresent(userCart -> {
            userCart.setStatus(CartStatus.CHECKED_OUT);
            userCart.setLastUpdated(LocalDateTime.now());
            cartDetailRepository.save(userCart);
            logger.info("Cart status set to CHECKED OUT for userId: {}", userId);
        });

        customRedisCache
                .getItemFromCache(userId, CartItemResponseDTO.class)
                .ifPresent(userCart -> customRedisCache.deleteItemInCache(userId));
    }
}
