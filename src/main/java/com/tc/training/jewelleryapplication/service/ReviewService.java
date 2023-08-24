package com.tc.training.jewelleryapplication.service;

import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.model.Review;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);
}
