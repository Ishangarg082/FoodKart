package com.fods.strategy;

import com.fods.entity.CartDetail;
import com.fods.entity.CartItemDetail;
import com.fods.mapper.CartServiceMapper;
import com.fods.model.CartItemResponseDTO;
import com.fods.model.enums.CartStatus;
import com.fods.repository.CartDetailRepository;
import com.fods.repository.CustomRedisCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StandardCartPersistenceStrategy implements CartPersistenceStrategy{

    private static final Logger logger = LogManager.getLogger(StandardCartPersistenceStrategy.class);

    private final CartDetailRepository cartDetailRepository;
    private final CustomRedisCache customRedisCache;

    public StandardCartPersistenceStrategy(CartDetailRepository cartDetailRepository,
                                           CustomRedisCache customRedisCache) {
        this.cartDetailRepository = cartDetailRepository;
        this.customRedisCache = customRedisCache;
    }

    @Override
    @Transactional
    public void persistCart(UUID userId, CartItemResponseDTO cartItem) {
        CartDetail cartDetail = CartServiceMapper.toCartDetail(cartItem);

        List<CartItemDetail> cartItems = cartItem.getCartItemsList().stream()
                .map(item -> {
                    CartItemDetail detail = CartServiceMapper.toCartItemDetail(item, cartItem.getRestaurantId());
                    detail.setCart(cartDetail);
                    return detail;
                })
                .collect(Collectors.toList());

        cartDetail.setStatus(CartStatus.ABANDONED);
        cartDetail.setCartItemDetails(cartItems);

        cartDetailRepository.save(cartDetail);
        customRedisCache.deleteItemInCache(userId);
    }
}
