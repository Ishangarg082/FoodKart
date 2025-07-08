package com.fods.restaurant.repository;

import com.fods.restaurant.entity.MenuItem;
import com.fods.restaurant.model.MenuItemNameAndPriceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<List<MenuItem>> findByRestaurantIdAndRestaurantNameAndIsAvailableIsTrue(long restaurantId,
                                                                                     String restaurantName);

    @Query("SELECT m.name FROM MenuItem m WHERE m.id = :menuId AND m.restaurant.id = :restaurantId")
    Optional<String> findNameByIdAndRestaurantId(@Param("menuId") Long menuId,
                                                 @Param("restaurantId") Long restaurantId);

    @Query("SELECT m.price FROM MenuItem m WHERE m.id = :menuId AND m.restaurant.id = :restaurantId")
    Optional<BigDecimal> findPriceByIdAndRestaurantId(@Param("menuId") Long menuId,
                                                      @Param("restaurantId") Long restaurantId);

    @Query("SELECT new com.fods.restaurant.model.MenuItemNameAndPriceDTO(m.name, m.price, m.isAvailable, m.imageUrl) " +
            "FROM MenuItem m WHERE m.id = :menuId AND m.restaurant.id = :restaurantId")
    Optional<MenuItemNameAndPriceDTO> findNameAndPriceByIdAndRestaurantId(@Param("menuId") Long menuId,
                                                                          @Param("restaurantId") Long restaurantId);
}
