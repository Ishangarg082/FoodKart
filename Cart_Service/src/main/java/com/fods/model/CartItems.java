package com.fods.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CartItems {
    private long menuItemId;
    private String itemName;
    private int quantity;
    private BigDecimal price;

    @Override
    public String toString() {
        return "CartItems{" +
                "menuItemId=" + menuItemId +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
