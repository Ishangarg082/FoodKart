package com.fods.controller;

import com.fods.model.HomePageResponseDTO;
import com.fods.service.HomePageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/home")
public class HomePageController {

    private final HomePageService homePageService;

    public HomePageController(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @GetMapping("/get-details")
    public ResponseEntity<HomePageResponseDTO> getHomePageDetails() {
        HomePageResponseDTO homePageResponse = homePageService.getHomePageResponse();
        if(homePageResponse != null)
            return ResponseEntity.ok(homePageResponse);
        return ResponseEntity.noContent().build();
    }
}
