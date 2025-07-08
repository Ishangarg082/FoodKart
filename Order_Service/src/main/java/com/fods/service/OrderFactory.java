package com.fods.service;

import com.fods.client.RestaurantServiceClient;
import com.fods.entity.Order;
import com.fods.entity.OrderHistory;
import com.fods.entity.OrderItem;
import com.fods.mapper.OrderServiceMapper;
import com.fods.model.OrderSubmitRequestDTO;
import com.fods.model.RestaurantDetailsResponseDTO;
import com.fods.model.RestaurantOrderRequestDTO;
import com.fods.model.ValidatedOrderItemDTO;
import com.fods.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class OrderFactory {

    private final RestaurantServiceClient restaurantServiceClient;

    public Order createOrder(OrderSubmitRequestDTO dto,
                             UUID userId,
                             UUID orderId,
                             UUID idempotencyKey,
                             OrderStatus status,
                             UUID paymentId,
                             BigDecimal totalPrice) {
        Order order = Order.builder()
                .orderId(orderId)
                .userId(userId)
                .restaurantId(dto.getRestaurantId())
                .orderStatus(status)
                .deliveryAddress(dto.getUserMetaDataDetail().getAddress())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .idempotencyKey(idempotencyKey)
                .paymentId(paymentId)
                .totalAmount(totalPrice)
                .build();
        List<OrderItem> orderItemList = dto
                .getOrderItemsList()
                .stream()
                .map(OrderServiceMapper::toOrderItem)
                .peek(item -> item.setOrder(order))
                .toList();
        order.setOrderItemList(orderItemList);
        return order;
    }

    public OrderHistory createOrderHistory(long restaurantId,
                                           OrderStatus orderStatus,
                                           List<ValidatedOrderItemDTO> orderDetails) {
        RestaurantDetailsResponseDTO restaurantDetails = restaurantServiceClient.getRestaurantDetails(restaurantId);
        return OrderServiceMapper.toOrderHistory(orderStatus, restaurantDetails, orderDetails);
    }

    public RestaurantOrderRequestDTO createRestaurantOrderRequestDTO(OrderSubmitRequestDTO orderSubmitRequestDTO, UUID orderId) {

    }
}
