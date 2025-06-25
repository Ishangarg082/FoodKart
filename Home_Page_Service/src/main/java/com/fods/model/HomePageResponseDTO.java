package com.fods.model;

import java.util.List;

public record HomePageResponseDTO(
        List<RestaurantSummaryDTO> topRatedRestaurants,
        List<RestaurantSummaryDTO> nearByRestaurants,
        List<TagDTO> pupularTags
) {
}
