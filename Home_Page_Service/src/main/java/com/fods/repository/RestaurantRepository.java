package com.fods.repository;

import com.fods.entity.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    List<Restaurant> findTop10ByOrderByAverageRatingDesc();
    @EntityGraph(attributePaths = {"menuItems"})
    List<Restaurant> findTop10ByIsOpenTrueOrderByAverageRatingDesc();
}
