package com.fods.client;

import com.fods.model.RestaurantDetailsResponseDTO;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class RestaurantServiceClient {
    private static final Logger logger = LogManager.getLogger(RestaurantServiceClient.class);

    private static WebClient.Builder webClientBuilder;

    public RestaurantDetailsResponseDTO getRestaurantDetails(long restaurantId) {

        String uri = String.format("http://Restaurant-Service/v1/restaurant/owner/%d/restaurant-details", restaurantId);
        try {
            return webClientBuilder.build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(RestaurantDetailsResponseDTO.class)
                    .block();
        } catch (Exception e) {
            logger.error("Failed to fetch restaurant details for restaurantId: {}", restaurantId, e);
            throw new RuntimeException("Failed to fetch restaurant details", e);
        }
    }
}
