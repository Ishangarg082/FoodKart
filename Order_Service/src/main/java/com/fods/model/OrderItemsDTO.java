package com.fods.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderItemsDTO {
    private long menuItemId;
    private String itemName;
    private int quantity;
    private BigDecimal price;

    @Override
    public String toString() {
        return "OrderItems{" +
                "menuItemId=" + menuItemId +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
