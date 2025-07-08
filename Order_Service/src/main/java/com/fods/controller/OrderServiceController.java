package com.fods.controller;

import com.fods.exception.DuplicateOrderException;
import com.fods.model.OrderSubmitRequestDTO;
import com.fods.model.OrderValidationResponse;
import com.fods.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/order")
public class OrderServiceController {
    private final OrderServiceImpl orderService;

    public OrderServiceController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    // Place_Order End-point
    @PostMapping("/place")
    public ResponseEntity<OrderValidationResponse> placeOrder(@RequestParam("userId") UUID userId,
                                                              @RequestParam("idempotencyKey") UUID idempotencyKey,
                                                              @RequestBody OrderSubmitRequestDTO requestDTO) {
        try {
            OrderValidationResponse response = orderService.placeOrder(requestDTO, userId, idempotencyKey);

            return switch (response.getStatus()) {
                case "VALID" -> ResponseEntity.ok(response);
                case "INVALID", "FAILED" -> ResponseEntity.status(HttpStatus.CONFLICT).body(response);
                case "SUCCESS" -> ResponseEntity.status(HttpStatus.CREATED).body(response);
                case "PAYMENT_FAILED" -> ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(response);
                default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            };

        } catch (DuplicateOrderException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    OrderValidationResponse.builder()
                            .status("DUPLICATE")
                            .message(e.getMessage())
                            .build()
            );
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    OrderValidationResponse.builder()
                            .status("ERROR")
                            .message("Unexpected error occurred: " + e.getMessage())
                            .build()
            );
        }


    }

    // Cancel Current order

    // Fetch Past Order for particular user

    // See current order
}
