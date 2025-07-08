package com.fods.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fods.model.enums.OrderStatus;

@Entity
@Table(name = "order_history")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_history_id", updatable = false, nullable = false)
    private UUID orderHistoryId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @Column(name = "restaurant_address", nullable = false)
    private String restaurantAddress;

    @Column(name = "item_summary", columnDefinition = "TEXT")
    private List<String> itemSummary;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;
}
