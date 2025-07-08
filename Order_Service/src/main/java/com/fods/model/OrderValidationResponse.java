package com.fods.model;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderValidationResponse {
    private UUID orderId;
    private String status;
    private String message;
    private BigDecimal correctedTotal;
    private List<ValidatedOrderItemDTO> updatedOrderItems;
    private List<String> cartChangeLogs;
}
