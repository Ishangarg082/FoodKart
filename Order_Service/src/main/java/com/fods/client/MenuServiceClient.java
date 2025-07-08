package com.fods.client;

import com.fods.exception.ExternalServiceException;
import com.fods.model.MenuItemDetailResponseDTO;
import com.fods.model.MenuItemNameAndPriceDTO;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class MenuServiceClient {

    private static final Logger logger = LogManager.getLogger(MenuServiceClient.class);

    private final WebClient.Builder webClientBuilder;

    public MenuItemNameAndPriceDTO getMenuItemDetails(long menuId, long restaurantId) {
        try {
            return fetchMenuItemDetailsAsync(menuId, restaurantId).get().getData();
        } catch (InterruptedException | ExecutionException | WebClientResponseException e) {
            Thread.currentThread().interrupt();
            logger.error("Failed to fetch item details for menuId: {}, restaurantId: {}", menuId, restaurantId, e);
            throw new ExternalServiceException("Failed to fetch item details", e);
        }
    }


    private CompletableFuture<MenuItemDetailResponseDTO<MenuItemNameAndPriceDTO>> fetchMenuItemDetailsAsync(long menuId,
                                                                                                long restaurantId) {
        String uri = String.format("http://Restaurant-Service/v1/restaurant/menu/%d/menu/%d/detail",
                restaurantId,
                menuId);
        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<MenuItemDetailResponseDTO<MenuItemNameAndPriceDTO>>() {
                })
                .timeout(Duration.ofSeconds(10))
                .toFuture();
    }
}
