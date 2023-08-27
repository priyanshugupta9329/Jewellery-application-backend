package com.tc.training.jewelleryapplication.controller;

import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.Product;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.service.RecommendationService;
import com.tc.training.jewelleryapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private RecommendationService recommendationService;
    private UserService userService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService, UserService userService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> recommendationForUser(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Product> products = recommendationService.generateRecommendations(user.getId());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}