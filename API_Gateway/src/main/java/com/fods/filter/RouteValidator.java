package com.fods.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openAPIEndpoints = List.of(
            "/v1/auth/register",
            "/v1/auth/token",
            "/v1/auth/",     // to cover any other auth endpoints if needed
            "/v1/auth/logout",
            "/v1/home/",     // public home page service
            "/v1/users/register",
            "/v1/users/login",
            "/eureka",       // eureka dashboard/registration endpoint
            "/actuator"      // optional: if you want actuator open
    );

    public Predicate<ServerHttpRequest> isSecured = request -> openAPIEndpoints
            .stream()
            .noneMatch(uri -> request
                    .getURI()
                    .getPath()
                    .contains(uri)
            );
}
