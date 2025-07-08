package com.fods.service;

import com.fods.entity.Order;
import com.fods.entity.OrderHistory;
import com.fods.entity.OrderItem;
import com.fods.exception.DuplicateOrderException;
import com.fods.mapper.OrderServiceMapper;
import com.fods.model.OrderSubmitRequestDTO;
import com.fods.model.OrderValidationResponse;
import com.fods.model.OrderValidationResult;
import com.fods.model.enums.OrderStatus;
import com.fods.repository.OrderRepository;
import com.fods.service.validator.OrderValidator;
import com.fods.utils.KafkaTopicNames;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderFactory orderFactory;
    private final KafkaEventPublisher kafkaEventPublisher;

    public OrderValidationResponse placeOrder(OrderSubmitRequestDTO orderSubmitRequestDTO,
                                              UUID userId,
                                              UUID idempotencyKey) {

        if (orderRepository.existsByIdempotencyKey(idempotencyKey)) {
            throw new DuplicateOrderException("Order already processed with this key");
        }

        UUID orderId = UUID.randomUUID();


        List<OrderItem> orderItemList = orderSubmitRequestDTO
                .getOrderItemsList()
                .stream()
                .map(OrderServiceMapper::toOrderItem)
                .toList();

        OrderValidationResult validation = orderValidator.validate(orderSubmitRequestDTO, orderItemList);


        if (!orderSubmitRequestDTO.isConfirmOrder()) {
            return OrderServiceMapper.buildValidationResponse(orderId, validation, false);
        }

        if (!validation.isValid()) {
            return OrderServiceMapper.buildValidationResponse(orderId, validation, true);
        }

        UUID paymentId = UUID.randomUUID();
        boolean isPaymentSuccessful = true;

        // Create a Kafka Producer Topic for the below two DB save operations.
        Order factoryOrder = orderFactory.createOrder(orderSubmitRequestDTO,
                userId,
                orderId,
                idempotencyKey,
                (isPaymentSuccessful ? OrderStatus.PAYMENT_SUCCESSFUL : OrderStatus.PAYMENT_FAILED),
                paymentId,
                validation.getFinalTotal());

        // To save to order details in DB
       kafkaEventPublisher.publishKafkaEvent(KafkaTopicNames.ORDER_CREATE, factoryOrder);

        OrderHistory orderHistory = orderFactory.createOrderHistory(orderSubmitRequestDTO.getRestaurantId(),
                (isPaymentSuccessful ? OrderStatus.PAYMENT_SUCCESSFUL : OrderStatus.PAYMENT_FAILED),
                validation.getUpdatedItems());

        // TO save the OrderHistory in DB
        kafkaEventPublisher.publishKafkaEvent(KafkaTopicNames.ORDER_HISTORY, orderHistory);

        // Send Kafka Request to Restaurant Service to accept the order

        return OrderValidationResponse.builder()
                .orderId(orderId)
                .status(isPaymentSuccessful ? "SUCCESS" : "PAYMENT_FAILED")
                .message(isPaymentSuccessful ? "Order placed successfully." : "Payment failed.")
                .correctedTotal(factoryOrder.getTotalAmount())
                .build();
    }
}
