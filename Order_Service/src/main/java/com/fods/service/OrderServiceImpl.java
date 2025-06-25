package com.fods.service;

import com.fods.entity.Order;
import com.fods.entity.OrderItem;
import com.fods.exception.DuplicateOrderException;
import com.fods.mapper.OrderServiceMapper;
import com.fods.model.OrderSubmitRequestDTO;
import com.fods.model.UserMetaDataDetailDTO;
import com.fods.model.enums.OrderStatus;
import com.fods.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void placeOrder(OrderSubmitRequestDTO orderSubmitRequestDTO, UUID userId, UUID idempotencyKey) {

        if(orderRepository.existsByIdempotencyKey(idempotencyKey)) {
            throw new DuplicateOrderException("Order already processed with this key");
        }

        UUID orderId = UUID.randomUUID();

        List<OrderItem> orderItemList = orderSubmitRequestDTO
                .getOrderItemsList()
                .stream()
                .map(OrderServiceMapper::toOrderItem)
                .toList();

        Order orderObject = Order
                .builder()
                .orderId(orderId)
                .userId(userId)
                .restaurantId(orderSubmitRequestDTO.getRestaurantId())
                .orderStatus(OrderStatus.INITIAL)
                .deliveryAddress(orderSubmitRequestDTO.getUserMetaDataDetail().getAddress())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .idempotencyKey(idempotencyKey)
                .orderItemList(orderItemList)
                .build();

        // Calling Payment Gateway
        boolean isPaymentSuccessful = true;

        if(isPaymentSuccessful) {
            orderObject.setPaymentId(UUID.randomUUID());
            orderObject.setOrderStatus(OrderStatus.PAYMENT_SUCCESSFUL);

            orderRepository.save(orderObject);
            // Publishing the Kafka Event to notify the restaurant to accept the Order
        } else {
            orderObject.setPaymentId(UUID.randomUUID());
            orderObject.setOrderStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.save(orderObject);
        }
    }
}
