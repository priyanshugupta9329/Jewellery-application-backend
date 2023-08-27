package com.tc.training.jewelleryapplication.service.Impl;

import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.model.Product;
import com.tc.training.jewelleryapplication.model.Rating;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.repository.RatingRepository;
import com.tc.training.jewelleryapplication.request.RatingRequest;
import com.tc.training.jewelleryapplication.service.ProductService;
import com.tc.training.jewelleryapplication.service.RatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RatingServiceImplementation implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());
        Rating rating =new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {


        return ratingRepository.getAllProductsRating(productId);
    }
}
