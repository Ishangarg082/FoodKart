package com.fods.restaurant.repository;

import com.fods.restaurant.entity.RestaurantLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantLocationRepository extends JpaRepository<RestaurantLocation, Long> {
}
