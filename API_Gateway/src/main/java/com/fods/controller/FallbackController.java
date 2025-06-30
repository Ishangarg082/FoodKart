package com.fods.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("fallback")
public class FallbackController {
    @GetMapping("/cart")
    @PostMapping("/cart")
    public Mono<ResponseEntity<Map<String, Object>>> cartFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createFallbackResponse("Cart Service",
                        "Cart service is temporarily unavailable. Please try again later.")));
    }

    @GetMapping("/users")
    @PostMapping("/users")
    public Mono<ResponseEntity<Map<String, Object>>> usersFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createFallbackResponse("Users Service",
                        "Cart service is temporarily unavailable. Please try again later.")));
    }

    @GetMapping("/home")
    @PostMapping("/home")
    public Mono<ResponseEntity<Map<String, Object>>> homeFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createFallbackResponse("Home Page Service",
                        "Cart service is temporarily unavailable. Please try again later.")));
    }

    @GetMapping("/restaurantMenu")
    @PostMapping("/restaurantMenu")
    public Mono<ResponseEntity<Map<String, Object>>> restaurantMenuFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createFallbackResponse("Restaurant Service",
                        "Cart service is temporarily unavailable. Please try again later.")));
    }

    @GetMapping("/restaurantOwner")
    @PostMapping("/restaurantOwner")
    public Mono<ResponseEntity<Map<String, Object>>> restaurantOwnerFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createFallbackResponse("Restaurant Owner Service",
                        "Cart service is temporarily unavailable. Please try again later.")));
    }

    @GetMapping("/order")
    @PostMapping("/order")
    public Mono<ResponseEntity<Map<String, Object>>> orderFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createFallbackResponse("Order Service",
                        "Cart service is temporarily unavailable. Please try again later.")));
    }

    private Map<String, Object> createFallbackResponse(String serviceName, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Service Unavailable");
        response.put("service", serviceName);
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        return response;
    }
}
