package com.fods.mapper;

import com.fods.entity.OrderItem;
import com.fods.model.OrderItemsDTO;

public class OrderServiceMapper {
    public static OrderItem toOrderItem(OrderItemsDTO orderItemsDTO) {
        return OrderItem
                .builder()
                .menuItemId(orderItemsDTO.getMenuItemId())
                .quantity(orderItemsDTO.getQuantity())
                .price(orderItemsDTO.getPrice())
                .build();
    }
}
