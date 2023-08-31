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

import java.util.*;

//@Service
//public class RecommendationServiceImplementation implements RecommendationService {
//
//    private OrderRepository orderRepository;
//
//    private ProductService productService;
//
//    @Autowired
//    public RecommendationServiceImplementation(OrderRepository orderRepository, ProductService productService) {
//        this.orderRepository = orderRepository;
//        this.productService = productService;
//    }
//
//    @Override
//    public List<Product> generateRecommendations(Long userId) throws UserException {
//        List<Order> userOrders = orderRepository.getUsersOrders(userId);
//
//        Set<String> categories = new HashSet<>();
//        for (Order order : userOrders) {
//            for (OrderItem orderItem : order.getOrderItems()) {
//                categories.add(orderItem.getProduct().getCategory().getName());
//            }
//        }
//
//        List<Product> recommendedProducts = new ArrayList<>();
//        for (String category : categories) {
//            recommendedProducts.addAll(productService.recommendProductsByCategory(category));
//        }
//
//        return recommendedProducts;
//    }


@Service
public class RecommendationServiceImplementation implements RecommendationService {

    private OrderRepository orderRepository;
    private ProductService productService;

    // Define the related categories mapping
    private Map<String, List<String>> relatedCategoriesMapping = new HashMap<>();

    @Autowired
    public RecommendationServiceImplementation(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;


        relatedCategoriesMapping.put("Earring", Arrays.asList("Pendant", "Finger_Ring"));
        relatedCategoriesMapping.put("Pendant", Arrays.asList("Earring", "Necklace"));
        relatedCategoriesMapping.put("Chain", Arrays.asList("Pendant", "Necklace"));
        relatedCategoriesMapping.put("Bangle", Arrays.asList("Bracelet"));
        relatedCategoriesMapping.put("Finger_Ring", Arrays.asList("Earring", "Pendant"));
        relatedCategoriesMapping.put("Mangalsutra", Arrays.asList("Necklace"));
        relatedCategoriesMapping.put("Necklace", Arrays.asList("Earrings", "Pendant", "Chain"));
        relatedCategoriesMapping.put("Bracelet", Arrays.asList("Bangle"));
        relatedCategoriesMapping.put("Nose_Pin", Arrays.asList("Earring"));
        relatedCategoriesMapping.put("Pendant_Set", Arrays.asList("Earring", "Pendant", "Necklace"));
        relatedCategoriesMapping.put("Engagement_Ring", Arrays.asList("Finger_Ring"));


    }

//    @Override
//    public List<Product> generateRecommendations(Long userId) throws UserException {
//        List<Order> userOrders = orderRepository.getUsersOrders(userId);
//
//        Set<String> categories = new HashSet<>();
//        for (Order order : userOrders) {
//            for (OrderItem orderItem : order.getOrderItems()) {
//                categories.add(orderItem.getProduct().getCategory().getName());
//            }
//        }
//
//        List<Product> recommendedProducts = new ArrayList<>();
//        for (String category : categories) {
//            recommendedProducts.addAll(productService.recommendProductsByCategory(category));
//
//            // Check if there are related categories for the current category
//            if (relatedCategoriesMapping.containsKey(category)) {
//                List<String> relatedCategories = relatedCategoriesMapping.get(category);
//                for (String relatedCategory : relatedCategories) {
//                    recommendedProducts.addAll(productService.recommendProductsByCategory(relatedCategory));
//                }
//            }
//        }
//
//        return recommendedProducts;
//    }


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
        Map<String, Integer> categoryProductCount = new HashMap<>();

        for (String category : categories) {
            List<Product> categoryProducts = productService.recommendProductsByCategory(category);


            if (relatedCategoriesMapping.containsKey(category)) {
                List<String> relatedCategories = relatedCategoriesMapping.get(category);
                for (String relatedCategory : relatedCategories) {
                    categoryProducts.addAll(productService.recommendProductsByCategory(relatedCategory));
                }
            }


            int productsAdded = 0;
            for (Product product : categoryProducts) {
                if (productsAdded < 2) {
                    recommendedProducts.add(product);
                    productsAdded++;
                }
            }


            categoryProductCount.put(category, productsAdded);
        }

        return recommendedProducts;
    }


    @Override
    public List<Product> generateRecommendationsForNewUser() {
        List<Product> recommendedProducts = new ArrayList<>();
        Map<String, Integer> categoryProductCount = new HashMap<>();


        List<String> randomCategories = getRandomCategories(2);

        for (String category : randomCategories) {
            List<Product> categoryProducts = productService.recommendProductsByCategory(category);

            if (relatedCategoriesMapping.containsKey(category)) {
                List<String> relatedCategories = relatedCategoriesMapping.get(category);
                for (String relatedCategory : relatedCategories) {
                    categoryProducts.addAll(productService.recommendProductsByCategory(relatedCategory));
                }
            }

            int productsAdded = 0;
            for (Product product : categoryProducts) {
                if (productsAdded < 3) {
                    recommendedProducts.add(product);
                    productsAdded++;
                }
            }

            categoryProductCount.put(category, productsAdded);
        }

        return recommendedProducts;
    }

    private List<String> getRandomCategories(int count) {
        List<String> allCategories = new ArrayList<>(relatedCategoriesMapping.keySet());
        Collections.shuffle(allCategories);
        return allCategories.subList(0, Math.min(count, allCategories.size()));
    }



}


