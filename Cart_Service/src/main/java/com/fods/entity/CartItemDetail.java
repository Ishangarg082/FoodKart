package com.fods.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_item")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDetail {
    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private CartDetail cart;

    @Column(name = "menu_item_id")
    private long menuItemId;

    @Column(name = "restaurant_id")
    private long restaurantId;

    private int quantity;

    private BigDecimal price;

}
