package com.fods.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CartItemResponseDTO {
    private UUID cartId;
    private UUID userId;
    private long restaurantId;
    private List<CartItems> cartItemsList;
    private BigDecimal totalPrice;
    private String lastUpdatedTime;

    @Override
    public String toString() {
        return "CartItemResponseDTO{" +
                "restaurantId=" + restaurantId +
                ", cartItemsList=" + cartItemsList +
                ", totalPrice=" + totalPrice +
                ", lastUpdatedTime='" + lastUpdatedTime + '\'' +
                '}';
    }

}
