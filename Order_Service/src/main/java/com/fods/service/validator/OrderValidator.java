package com.fods.service.validator;


import com.fods.entity.OrderItem;
import com.fods.model.OrderSubmitRequestDTO;
import com.fods.model.OrderValidationResult;

import java.util.List;

public interface OrderValidator {
    OrderValidationResult validate(OrderSubmitRequestDTO requestDTO, List<OrderItem> submittedItems);
}
