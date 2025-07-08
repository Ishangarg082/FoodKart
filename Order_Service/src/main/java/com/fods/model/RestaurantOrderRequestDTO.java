package com.fods.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RestaurantOrderRequestDTO {
    private UUID orderId;
    private List<OrderItemsDTO> orderItemsList;
    private BigDecimal totalAmount;
    private UserMetaDataDetailDTO userMetaDataDetail;
}
