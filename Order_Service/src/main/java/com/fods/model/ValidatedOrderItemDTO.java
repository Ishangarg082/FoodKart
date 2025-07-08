package com.fods.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidatedOrderItemDTO {
    private Long menuItemId;
    private String name;
    private int quantity;
    private BigDecimal oldPrice;
    private BigDecimal oldTotalPrice;
    private BigDecimal updatedPrice;
    private BigDecimal updatedTotalPrice;
    private boolean available;
    private boolean isPriceChanged;
}
