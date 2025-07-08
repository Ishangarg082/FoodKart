package com.fods.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class OrderValidationResult {
    private boolean valid;
    private String message;
    private BigDecimal finalTotal;
    private List<ValidatedOrderItemDTO> updatedItems;
    private List<String> changeLogs;
}
