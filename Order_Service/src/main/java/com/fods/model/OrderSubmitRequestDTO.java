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
public class OrderSubmitRequestDTO {
    private UUID cartId;
    private long restaurantId;
    private List<OrderItemsDTO> orderItemsList;
    private UserMetaDataDetailDTO userMetaDataDetail;
    private BigDecimal totalAmount; // Add proper total amount breakup contains total taxes, delivery and handling charges
    private boolean confirmOrder = false;
}
