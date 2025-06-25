package com.fods.service;

import com.fods.entity.Restaurant;
import com.fods.entity.Tag;
import com.fods.exception.RestaurantFetchException;
import com.fods.exception.TagFetchException;
import com.fods.mapper.HomePageServiceMapper;
import com.fods.model.RestaurantSummaryDTO;
import com.fods.model.TagDTO;
import com.fods.repository.RestaurantRepository;
import com.fods.repository.TagsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class HomePageAsyncService {

    private final RestaurantRepository restaurantRepository;

    private final TagsRepository tagsRepository;

    private static final Logger logger = LogManager.getLogger(HomePageAsyncService.class);

    public HomePageAsyncService(RestaurantRepository restaurantRepository, TagsRepository tagsRepository) {
        this.restaurantRepository = restaurantRepository;
        this.tagsRepository = tagsRepository;
    }

    @Async("asyncThreadPoolExecutor")
    @Transactional
    public CompletableFuture<List<RestaurantSummaryDTO>> getTopRatedRestaurants() {
        logger.info("Thread_Name: {} | Inside getTopRatedRestaurants......... ", Thread.currentThread().getName());
        try {
            List<Restaurant> restaurantList = restaurantRepository.findTop10ByIsOpenTrueOrderByAverageRatingDesc();
            List<RestaurantSummaryDTO> summaryDTOS = restaurantList
                    .stream()
                    .map(HomePageServiceMapper::toRestaurantSummaryDTO)
                    .toList();
            return CompletableFuture.completedFuture(summaryDTOS);
        } catch (Exception e) {
            logger.error("Error fetching top-rated restaurants", e);
            throw new RestaurantFetchException("Failed to fetch top-rated restaurants", e);
        }
    }

    /*@Async("asyncThreadPoolExecutor")
    @Transactional
    public CompletableFuture<List<Restaurant>> getTopRatedRestaurants() {
        logger.info("Thread_Name: {} | Inside getTopRatedRestaurants......... ", Thread.currentThread().getName());
        try {
            List<Restaurant> restaurantList = restaurantRepository.findTop10ByIsOpenTrueOrderByAverageRatingDesc();
            return CompletableFuture.completedFuture(restaurantList);
        } catch (Exception e) {
            logger.error("Error fetching top-rated restaurants", e);
            throw new RestaurantFetchException("Failed to fetch top-rated restaurants", e);
        }
    }*/

    /*@Async("asyncThreadPoolExecutor")
    @Transactional
    public CompletableFuture<List<Tag>> getCuisineTags() {
        logger.info("Thread_Name: {} | Inside getCuisineTags......... ", Thread.currentThread().getName());
        try {
            List<Tag> tagList = tagsRepository.findAll();
            return CompletableFuture.completedFuture(tagList);
        } catch (Exception e) {
            logger.error("Error fetching tags", e);
            throw new TagFetchException("Failed to fetch cuisine tags", e);
        }
    }*/

    @Async("asyncThreadPoolExecutor")
    @Transactional
    public CompletableFuture<List<TagDTO>> getCuisineTags() {
        logger.info("Thread_Name: {} | Inside getCuisineTags......... ", Thread.currentThread().getName());
        try {
            List<Tag> tagList = tagsRepository.findAll();
            List<TagDTO> tagDTOS = tagList.stream().map(HomePageServiceMapper::toTagDTO).toList();
            return CompletableFuture.completedFuture(tagDTOS);
        } catch (Exception e) {
            logger.error("Error fetching tags", e);
            throw new TagFetchException("Failed to fetch cuisine tags", e);
        }
    }

}
