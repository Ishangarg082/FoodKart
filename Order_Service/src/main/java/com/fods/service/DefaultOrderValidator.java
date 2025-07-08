package com.fods.service;

import com.fods.client.MenuServiceClient;
import com.fods.entity.OrderItem;
import com.fods.model.MenuItemNameAndPriceDTO;
import com.fods.model.OrderSubmitRequestDTO;
import com.fods.model.OrderValidationResult;
import com.fods.model.ValidatedOrderItemDTO;
import com.fods.service.validator.OrderValidator;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultOrderValidator implements OrderValidator {

    private final static Logger logger = LogManager.getLogger(DefaultOrderValidator.class);

    private final MenuServiceClient menuServiceClient;

    @Override
    public OrderValidationResult validate(OrderSubmitRequestDTO requestDTO, List<OrderItem> submittedItems) {
        List<ValidatedOrderItemDTO> updatedItems = new ArrayList<>();
        List<String> changeLogs = new ArrayList<>();
        BigDecimal computedTotal = BigDecimal.ZERO;
        boolean hasError = false;

        for (OrderItem orderItem : submittedItems) {
            MenuItemNameAndPriceDTO menuItemDetails = menuServiceClient.getMenuItemDetails(orderItem.getMenuItemId(),
                    requestDTO.getRestaurantId());
            boolean available = menuItemDetails != null && menuItemDetails.isAvailable();

            BigDecimal currentPrice = available ? menuItemDetails.price() : BigDecimal.ZERO;
            BigDecimal cartPrice = orderItem.getPrice().divide(BigDecimal.valueOf(orderItem.getQuantity()),
                    2,
                    RoundingMode.HALF_UP);

            logger.info("Current Price for item {}: {}", orderItem.getMenuItemId(), currentPrice);
            logger.info("Cart Price for item {}: {}", orderItem.getMenuItemId(), cartPrice);


            ValidatedOrderItemDTO validatedItem = ValidatedOrderItemDTO.builder()
                    .menuItemId(orderItem.getMenuItemId())
                    .name(menuItemDetails != null ? menuItemDetails.name() : "Unknown")
                    .quantity(orderItem.getQuantity())
                    .oldPrice(cartPrice)
                    .updatedPrice(currentPrice)
                    .oldTotalPrice(orderItem.getPrice())
                    .updatedTotalPrice(currentPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                    .available(available)
                    .isPriceChanged(cartPrice.compareTo(currentPrice) != 0)
                    .build();
            updatedItems.add(validatedItem);


            if (!available) {
                hasError = true;
                changeLogs.add("Item '" + validatedItem.getName() + "' is no longer available.");
                continue;
            }

            if (cartPrice.compareTo(currentPrice) != 0) {
                hasError = true;
                changeLogs.add("Price changed for '" + validatedItem.getName() + "': ₹" + menuItemDetails.price() + " → ₹" + currentPrice);
            }

            computedTotal = computedTotal.add(currentPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            logger.info("Computed total after adding item {}: ₹{}", orderItem.getMenuItemId(), computedTotal);

        }

        /*BigDecimal delivery = BigDecimal.valueOf(30);
        BigDecimal tax = computedTotal.multiply(BigDecimal.valueOf(0.05));
        BigDecimal finalTotal = computedTotal.add(delivery).add(tax);*/

/*
        if (finalTotal.compareTo(requestDTO.getTotalAmount()) != 0) {
            hasError = true;
            changeLogs.add("Total amount changed: ₹" + requestDTO.getTotalAmount() + " → ₹" + finalTotal);
        }
*/

        return OrderValidationResult.builder()
                .valid(!hasError)
                .message(hasError ? "Cart has changed. Please review before confirming." : "Cart is valid.")
                .finalTotal(computedTotal)
                .updatedItems(updatedItems)
                .changeLogs(changeLogs)
                .build();

    }
}
