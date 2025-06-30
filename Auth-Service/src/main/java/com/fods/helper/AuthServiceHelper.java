package com.fods.helper;

import com.fods.model.UserDetailResponseDTO;
import com.fods.model.UserDetailsRequestDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class AuthServiceHelper {
    private static final Logger logger = LogManager.getLogger(AuthServiceHelper.class);
    private final RestTemplate restTemplate;

    public AuthServiceHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<UserDetailResponseDTO> saveUserDetails(UserDetailsRequestDTO userDetailsRequestDTO) {
        logger.info("Calling User-Service to save user details: {}", userDetailsRequestDTO.toString());
        String apiEndPointURL = "http://User-Service/v1/users/register";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<UserDetailResponseDTO> response = restTemplate.exchange(
                    apiEndPointURL,
                    HttpMethod.POST,
                    new HttpEntity<>(userDetailsRequestDTO, headers),
                    UserDetailResponseDTO.class
            );

            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.error("Error calling AuthService: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            return Optional.empty();
        } catch (Exception ex) {
            logger.error("Unexpected error: {}", ex.getMessage());
            return Optional.empty();
        }
    }
}
