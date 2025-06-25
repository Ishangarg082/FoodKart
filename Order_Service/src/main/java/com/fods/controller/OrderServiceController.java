package com.fods.controller;

import com.fods.service.OrderServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderServiceController {
    private final OrderServiceImpl orderService;

    public OrderServiceController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    // Place_Order End-point

    // Cancel Current order

    // Fetch Past Order for particular user

    // Edit the current order

    // See current order
}
