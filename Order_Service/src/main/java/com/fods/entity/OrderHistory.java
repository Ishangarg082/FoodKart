package com.fods.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private UUID orderHistoryId;

    private UUID userId;

    private String restaurantName;

    private String itemSummary;

    private BigDecimal totalAmount;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private String orderStatus;
}
