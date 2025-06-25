package com.fods.model;

import java.time.LocalTime;
import java.util.List;

public record RestaurantSummaryDTO(
        long restaurantId,
        String name,
        String description,
        float averageRating,
        int totalReviews,
        String city,
        String phone,
        String website,
        LocalTime openingTime,
        LocalTime closingTime,
        double latitude,
        double longitude,
        String imageUrl,
        List<String> tags
) {
}
