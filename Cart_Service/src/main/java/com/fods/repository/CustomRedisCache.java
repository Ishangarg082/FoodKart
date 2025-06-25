package com.fods.repository;

import com.fods.model.CartItemResponseDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class CustomRedisCache {

    private final static String USER_CACHE_REGION = "user_cart_detail";
    private final static String CART_KEY_PREFIX = "cart:";

    private final RedisTemplate<String, Object> redisTemplate;

    public CustomRedisCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> Optional<T> getItemFromCache(UUID userId, Class<T> classType) {
        String redisKey = CART_KEY_PREFIX + userId;
        Object o = redisTemplate.opsForHash().get(USER_CACHE_REGION, redisKey);
        return Optional.ofNullable(classType.cast(o));
    }

    public <T> void saveItemInCache(UUID userId, T dataObject) {
        String redisKey = CART_KEY_PREFIX + userId;
        redisTemplate.opsForHash().put(USER_CACHE_REGION, redisKey, dataObject);
    }

    public void deleteItemInCache(UUID userId) {
        redisTemplate.opsForHash().delete(CART_KEY_PREFIX + userId);
    }

    public Set<String> getAllUserCartDetailKey() {
        return redisTemplate.keys("cart:");
    }

    public Long getKeyExpiry(UUID userId) {
        return redisTemplate.getExpire(CART_KEY_PREFIX + userId);
    }

}
