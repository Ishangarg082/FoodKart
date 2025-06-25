package com.fods.service;

import com.fods.entity.Restaurant;
import com.fods.entity.Tag;
import com.fods.exception.RestaurantFetchException;
import com.fods.exception.TagFetchException;
import com.fods.mapper.HomePageServiceMapper;
import com.fods.model.HomePageResponseDTO;
import com.fods.model.RestaurantSummaryDTO;
import com.fods.model.TagDTO;
import com.fods.repository.RestaurantRepository;
import com.fods.repository.TagsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class HomePageServiceImpl implements HomePageService {
    private final RestaurantRepository restaurantRepository;
    private final TagsRepository tagsRepository;
    private final HomePageAsyncService homePageAsyncService;

    private static final Logger logger = LogManager.getLogger(HomePageServiceImpl.class);

    public HomePageServiceImpl(RestaurantRepository restaurantRepository,
                               TagsRepository tagsRepository,
                               HomePageAsyncService homePageAsyncService) {
        this.restaurantRepository = restaurantRepository;
        this.tagsRepository = tagsRepository;
        this.homePageAsyncService = homePageAsyncService;
    }

    @Override
    public HomePageResponseDTO getHomePageResponse() {
        logger.info("Inside getHomePageResponse()................");

        try {
            // Use Proper CompletableFuture Instead Async Annotation approach....
/*
            CompletableFuture<List<Restaurant>> topRatedRestaurants = homePageAsyncService.getTopRatedRestaurants();
            CompletableFuture<List<Tag>> cuisineTags = homePageAsyncService.getCuisineTags();

            CompletableFuture.allOf(topRatedRestaurants, cuisineTags).join();

            List<Restaurant> restaurantList = topRatedRestaurants.get();
            List<RestaurantSummaryDTO> summaryDTOS = restaurantList
                    .stream()
                    .map(HomePageServiceMapper::toRestaurantSummaryDTO)
                    .toList();

            List<Tag> tagList = cuisineTags.get();
            List<TagDTO> tagDTOS = tagList.stream().map(HomePageServiceMapper::toTagDTO).toList();
*/

            CompletableFuture<List<RestaurantSummaryDTO>> topRatedRestaurants1 = homePageAsyncService.getTopRatedRestaurants();
            CompletableFuture<List<TagDTO>> cuisineTags1 = homePageAsyncService.getCuisineTags();
            CompletableFuture.allOf(topRatedRestaurants1, cuisineTags1).join();

            List<RestaurantSummaryDTO> restaurantSummaryDTOS = topRatedRestaurants1.get();
            List<TagDTO> tagDTOS1 = cuisineTags1.get();

            /*return new HomePageResponseDTO(
                    summaryDTOS,
                    null,
                    tagDTOS
            );*/

            return new HomePageResponseDTO(
                    restaurantSummaryDTOS, null, tagDTOS1
            );

        } catch (Exception e) {
            throw new RuntimeException("Error building homepage response", e);
        }
    }
}
