package com.fods.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items", indexes = {
        @Index(name = "idx_menu_item_name", columnList = "name"),
        @Index(name = "idx_price", columnList = "price")
})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(nullable = false)
    private BigDecimal price;

    private Boolean isAvailable = true;

    private String imageUrl;
}

