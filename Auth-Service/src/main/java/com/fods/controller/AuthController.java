package com.fods.controller;

import com.fods.model.UserBasicDetailsDTO;
import com.fods.model.UserDetailResponseDTO;
import com.fods.model.UserDetailsRequestDTO;
import com.fods.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final static Logger logger = LogManager.getLogger(AuthController.class.getName());

    @PostMapping("/register")
    public ResponseEntity<UserDetailResponseDTO> addNewUser(@RequestBody UserDetailsRequestDTO userDetailsRequestDTO) {
        logger.info("Entering addNewUser method with request: {}", userDetailsRequestDTO.toString());
        UserDetailResponseDTO userDetailResponseDTO = authenticationService.saveUser(userDetailsRequestDTO);
        if (userDetailResponseDTO != null) {
            String jwtToken = authenticationService.generateToken(userDetailResponseDTO.userName());
            System.out.println(jwtToken);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-User-Registered", "true");
            headers.set("X-User-ID", userDetailResponseDTO.userId().toString());
            headers.set("token", "Bearer " + jwtToken);

            return ResponseEntity.ok().headers(headers).body(userDetailResponseDTO);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/token")
    public String getToken(@RequestBody UserBasicDetailsDTO userDetailsRequestDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDetailsRequestDTO.userName(),
                userDetailsRequestDTO.password()));

        if (authenticate.isAuthenticated())
            return authenticationService.generateToken(userDetailsRequestDTO.userName());
        throw new RuntimeException("Invalid Access");
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam("token") String token) {
        logger.info("Validating token: {}", token);
        return authenticationService.validateToken(token);
    }

    @GetMapping("/details")
    public Map<String, String> extractUserNameAndRole(@RequestParam("token") String token) {
        return authenticationService.getDetailsFromToken(token);
    }

}
