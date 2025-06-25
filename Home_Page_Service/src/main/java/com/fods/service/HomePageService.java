package com.fods.service;

import com.fods.entity.Restaurant;
import com.fods.entity.Tag;
import com.fods.model.HomePageResponseDTO;

import java.util.List;

public interface HomePageService {
    HomePageResponseDTO getHomePageResponse();
}
