package com.fods.filter;

import com.fods.helper.APIGatewayHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final static Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    public static class Config {
    }

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private APIGatewayHelper apiGatewayHelper;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (routeValidator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return apiGatewayHelper.onError(exchange,
                            "Authorization token is missing or has expired.",
                            HttpStatus.UNAUTHORIZED);
                }
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                logger.info("Authorization header with bearer: {}", authHeader);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return apiGatewayHelper.onError(exchange,
                            "Authorization header must start with 'Bearer '.",
                            HttpStatus.UNAUTHORIZED);
                }
                authHeader = authHeader.substring(7);
                logger.info("Authorization header without Bearer: {}", authHeader);
                return apiGatewayHelper.validateToken(authHeader).flatMap(isValid -> {
                            if (!isValid) {
                                return apiGatewayHelper.onError(exchange,
                                        "Authorization token is invalid or has expired.",
                                        HttpStatus.UNAUTHORIZED);
                            }
                            return chain.filter(exchange);
                        }
                ).onErrorResume(e -> {
                    logger.error("Token validation failed", e);
                    return apiGatewayHelper.onError(exchange,
                            "Token validation failed due to an internal error.",
                            HttpStatus.UNAUTHORIZED);
                });

                /*try {
                    if (!apiGatewayHelper.validateToken(authHeader)) {
                        return apiGatewayHelper.onError(exchange, "Authorization token is invalid or has expired.", HttpStatus.UNAUTHORIZED);
                    }

                    *//*Map<String, String> usernameAndRole = apiGatewayHelper.getUsernameAndRole(authHeader);
                    ServerHttpRequest modifiedRequest = request
                            .mutate()
                            .header("X-User-Id", usernameAndRole.get("username"))
                            .build();

                    ServerWebExchange serverWebExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();*//*

                    return chain.filter(exchange);
                } catch (Exception e) {
                    logger.error("xfailed", e);
                    return apiGatewayHelper.onError(exchange, "Token validation failed due to an internal error.", HttpStatus.UNAUTHORIZED);
                }*/
            }
            return chain.filter(exchange);
        };

    }
}
