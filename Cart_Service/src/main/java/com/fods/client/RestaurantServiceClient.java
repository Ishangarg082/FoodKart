package com.fods.client;

import com.fods.exception.ExternalServiceException;
import com.fods.model.MenuItemDetailResponseDTO;
import com.fods.model.MenuItemNameAndPriceDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class RestaurantServiceClient {

    private static final Logger logger = LogManager.getLogger(RestaurantServiceClient.class);

    private final WebClient.Builder webClientBuilder;

    public RestaurantServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public MenuItemNameAndPriceDTO fetchItemDetail(long menuId, long restaurantId) {
        try {
            return fetchItemDetailAsync(menuId, restaurantId).get().getData();
        } catch (InterruptedException | ExecutionException | WebClientResponseException e) {
            Thread.currentThread().interrupt();
            logger.error("Failed to fetch item details for menuId: {}, restaurantId: {}", menuId, restaurantId, e);
            throw new ExternalServiceException("Failed to fetch item details", e);
        }
    }

    private CompletableFuture<MenuItemDetailResponseDTO<MenuItemNameAndPriceDTO>> fetchItemDetailAsync(long menuId,
                                                                                                       long restaurantId) {
//        String uri = String.format("http://localhost:9092/v1/restaurant/%d/menu/%d/detail", restaurantId, menuId);
        String uri = String.format("http://Restaurant-Service/v1/restaurant/menu/%d/menu/%d/detail", restaurantId, menuId);

        logger.info("URI: {}", uri);

        return webClientBuilder
                .build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<MenuItemDetailResponseDTO<MenuItemNameAndPriceDTO>>() {
                })
                .timeout(Duration.ofSeconds(10))
                .toFuture();
    }
}
