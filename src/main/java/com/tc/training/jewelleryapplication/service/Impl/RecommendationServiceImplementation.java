package com.tc.training.jewelleryapplication.service.Impl;

import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.Order;
import com.tc.training.jewelleryapplication.model.OrderItem;
import com.tc.training.jewelleryapplication.model.Product;
import com.tc.training.jewelleryapplication.repository.OrderRepository;
import com.tc.training.jewelleryapplication.service.ProductService;
import com.tc.training.jewelleryapplication.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationServiceImplementation implements RecommendationService {

    private OrderRepository orderRepository;

    private ProductService productService;

    @Autowired
    public RecommendationServiceImplementation(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> generateRecommendations(Long userId) throws UserException {
        List<Order> userOrders = orderRepository.getUsersOrders(userId);

        Set<String> categories = new HashSet<>();
        for (Order order : userOrders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                categories.add(orderItem.getProduct().getCategory().getName());
            }
        }

        List<Product> recommendedProducts = new ArrayList<>();
        for (String category : categories) {
            recommendedProducts.addAll(productService.recommendProductsByCategory(category));
        }

        return recommendedProducts;
    }

}

