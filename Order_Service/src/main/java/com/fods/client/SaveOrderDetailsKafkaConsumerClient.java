package com.fods.client;

import com.fods.entity.Order;
import com.fods.entity.OrderHistory;
import com.fods.repository.OrderHistoryRepository;
import com.fods.repository.OrderRepository;
import com.fods.utils.KafkaTopicNames;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SaveOrderDetailsKafkaConsumerClient {

    private static final Logger logger = LogManager.getLogger(SaveOrderDetailsKafkaConsumerClient.class);

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;


    @KafkaListener(topics = KafkaTopicNames.ORDER_CREATE, groupId = "order_group")
    public void saveOrderDetailsInDB(Order order) {
        try {
            orderRepository.save(order);
            logger.info("Order saved: {}", order.getOrderId());
        } catch (Exception e) {
            logger.error("Error saving order", e);
            throw new RuntimeException("Failed to save order.");
        }
    }

    @KafkaListener(topics = KafkaTopicNames.ORDER_HISTORY, groupId = "order_group")
    public void saveOrderHistoryDetailsInDB(OrderHistory orderHistory) {
        try {
            orderHistoryRepository.save(orderHistory);
            logger.info("Order history saved for order: {}", orderHistory.getOrderHistoryId());
        } catch (Exception e) {
            logger.error("Error saving order history", e);
            throw new RuntimeException("Failed to save order history.");
        }
    }

}
