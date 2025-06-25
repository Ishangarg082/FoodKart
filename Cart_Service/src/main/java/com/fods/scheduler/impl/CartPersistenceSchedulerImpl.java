package com.fods.scheduler.impl;

import com.fods.model.CartItemResponseDTO;
import com.fods.repository.CustomRedisCache;
import com.fods.scheduler.CartPersistenceScheduler;
import com.fods.strategy.CartPersistenceStrategy;
import com.fods.utility.CartServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CartPersistenceSchedulerImpl implements CartPersistenceScheduler {

    private static final Logger logger = LogManager.getLogger(CartPersistenceSchedulerImpl.class);

    private final CartPersistenceStrategy cartPersistenceStrategy;
    private final CustomRedisCache customRedisCache;

    public CartPersistenceSchedulerImpl(CartPersistenceStrategy cartPersistenceStrategy,
                                        CustomRedisCache customRedisCache) {
        this.cartPersistenceStrategy = cartPersistenceStrategy;
        this.customRedisCache = customRedisCache;
    }

    @Override
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void persistAllCartsToDatabase() {
        Set<String> allUserCartDetailKey = customRedisCache.getAllUserCartDetailKey();

        if (allUserCartDetailKey.isEmpty()) {
            logger.info("No cart keys found in Redis to persist in DB.");
            return;
        }

        allUserCartDetailKey.stream()
                .map(CartServiceUtils::extractUserIdFromCacheKey)
                .forEach(userId -> customRedisCache.getItemFromCache(userId, CartItemResponseDTO.class)
                        .filter(item -> CartServiceUtils.isInCartActiveByLastModified(item.getLastUpdatedTime()))
                        .ifPresent(item -> {
                            try {
                                logger.info("Persisting cart for user: {}", userId);
                                cartPersistenceStrategy.persistCart(userId, item);
                            } catch (Exception e) {
                                logger.error("Error persisting cart for user {}: {}", userId, e.getMessage(), e);
                            }
                        }));
    }
}
