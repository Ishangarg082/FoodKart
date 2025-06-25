package com.fods.utility;

import com.fods.model.CartItems;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public class CartServiceUtils {
    public static BigDecimal calculateItemTotal(int quantity, BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static BigDecimal calculateCartTotal(List<CartItems> items) {
        return items.stream()
                .map(CartItems::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static boolean isInCartActiveByLastModified(String lastModifiedDate) {
        try {
            long epochSeconds = Long.parseLong(lastModifiedDate);
            LocalDateTime lastModified = Instant
                    .ofEpochSecond(epochSeconds)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return lastModified.isAfter(LocalDateTime.now().minusMinutes(45));
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static UUID extractUserIdFromCacheKey(String cacheKey) {
        return UUID.fromString(cacheKey.replace("cart:", ""));
    }
}
