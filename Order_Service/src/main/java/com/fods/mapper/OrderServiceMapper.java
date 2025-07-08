package com.fods.mapper;

import com.fods.entity.Order;
import com.fods.entity.OrderHistory;
import com.fods.entity.OrderItem;
import com.fods.model.*;
import com.fods.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderServiceMapper {
    public static OrderItem toOrderItem(OrderItemsDTO orderItemsDTO) {
        return OrderItem
                .builder()
                .menuItemId(orderItemsDTO.getMenuItemId())
                .quantity(orderItemsDTO.getQuantity())
                .price(orderItemsDTO.getPrice())
                .build();
    }

    public static OrderValidationResponse buildValidationResponse(UUID orderId,
                                                                  OrderValidationResult result,
                                                                  boolean isConfirmedPhase) {
        return OrderValidationResponse
                .builder()
                .orderId(orderId)
                .status(isConfirmedPhase ?
                        "FAILED" :
                        result.isValid() ? "VALID" : "INVALID")
                .message(result.getMessage())
                .correctedTotal(result.getFinalTotal())
                .updatedOrderItems(result.getUpdatedItems())
                .cartChangeLogs(result.getChangeLogs())
                .build();
    }

    public static OrderHistory toOrderHistory(OrderStatus orderStatus,
                                              RestaurantDetailsResponseDTO restaurantDetails,
                                              List<ValidatedOrderItemDTO> orderItemDTO) {
        return OrderHistory
                .builder()
                .restaurantName(restaurantDetails.getRestaurantName())
                .itemSummary(getItemSummary(orderItemDTO))
                .restaurantAddress(restaurantDetails.getRestaurantAddress())
                .orderDate(LocalDateTime.now())
                .orderStatus(orderStatus)
                .build();
    }

    public static List<String> getItemSummary(List<ValidatedOrderItemDTO> orderItem) {
        return orderItem.stream().map(item -> item.getName() + " * " + item.getQuantity()).toList();
    }
}
