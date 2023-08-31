package com.tc.training.jewelleryapplication.service;

import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.Product;

import java.util.List;

public interface RecommendationService {

    List<Product> generateRecommendations(Long userId) throws UserException;

    public List<Product> generateRecommendationsForNewUser();
}
