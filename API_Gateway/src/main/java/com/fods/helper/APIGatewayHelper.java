package com.fods.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class APIGatewayHelper {
    private static final Logger logger = LogManager.getLogger(APIGatewayHelper.class);
    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    public APIGatewayHelper(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Content-Type", "application/json");
        response.getHeaders().add("X-Auth-Error", err); // Optional custom header

        String errorResponse = String.format("{\"error\": \"%s\", \"status\": %d}", err, httpStatus.value());

        logger.info("Error Response: {}", errorResponse);

        byte[] bytes = errorResponse.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer))
                .doOnError(ex -> {
                    // Optional: log error
                    System.err.println("Error writing response: " + ex.getMessage());
                });
    }

    public Mono<Boolean> validateToken(String token) {
        String apiEndPointURL = "http://Auth-Service/v1/auth/validate";
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("Auth-Service")
                        .path("/v1/auth/validate")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class);
        // fallback in case of error
    }

    /*public boolean validateToken(String token) {
        String apiEndPointURL = "http://Auth-Service/v1/auth/validate";
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    apiEndPointURL,
                    HttpMethod.GET,
                    entity,
                    Boolean.class
            );

            return response.getBody() != null && response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // token invalid or other HTTP error
            return false;
        }
    }*/

    public Map<String, String> getUsernameAndRole(String token) {
        String apiEndPointURL = "http://Auth-Service/v1/auth/details";
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                    apiEndPointURL,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, String>>() {
                    }
            );

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // token invalid or other HTTP error
            return null;
        }
    }
}
