package com.fods.mapper;

import com.fods.entity.Restaurant;
import com.fods.entity.Tag;
import com.fods.model.RestaurantSummaryDTO;
import com.fods.model.TagDTO;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
* long restaurantId,
        String name,
        String description,
        float averageRating,
        String city,
        double latitude,
        double longitude,
        List<String> tags
* */

public class HomePageServiceMapper {

    private static List<String> getTagList(Set<Tag> tagSet) {
        return tagSet.stream().map(Tag::getName).toList();
    }

    public static RestaurantSummaryDTO toRestaurantSummaryDTO(Restaurant restaurant) {
        return new RestaurantSummaryDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getAverageRating(),
                restaurant.getTotalReviews(),
                Hibernate.isInitialized(restaurant.getLocation()) && restaurant.getLocation() != null ?
                        restaurant.getLocation().getCity() :
                        null,
                restaurant.getPhone(),
                restaurant.getWebsite(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime(),
                Hibernate.isInitialized(restaurant.getLocation()) && restaurant.getLocation() != null ?
                        restaurant.getLocation().getLatitude() :
                        0,
                Hibernate.isInitialized(restaurant.getLocation()) && restaurant.getLocation() != null ?
                        restaurant.getLocation().getLongitude() :
                        0,
                Hibernate.isInitialized(restaurant.getMenuItems()) && restaurant.getMenuItems() != null ?
                        restaurant.getMenuItems().get(0).getImageUrl() :
                        null,
                getTagList(restaurant.getTags())
        );
    }

    public static TagDTO toTagDTO(Tag tag) {
        return new TagDTO(
                tag.getId(),
                tag.getName()
        );
    }
}
