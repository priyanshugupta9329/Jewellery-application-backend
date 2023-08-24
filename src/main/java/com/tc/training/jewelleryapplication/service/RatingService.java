package com.tc.training.jewelleryapplication.service;


import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.model.Rating;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);
}
