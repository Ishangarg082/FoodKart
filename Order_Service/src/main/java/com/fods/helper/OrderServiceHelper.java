package com.fods.helper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class OrderServiceHelper {
    private final WebClient.Builder webClientBuilder;


}
