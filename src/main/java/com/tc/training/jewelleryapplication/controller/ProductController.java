package com.tc.training.jewelleryapplication.controller;


import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.Product;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.service.ProductService;
import com.tc.training.jewelleryapplication.service.RecommendationService;
import com.tc.training.jewelleryapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/public")
public class ProductController {


    @Autowired
    private ProductService productService;


    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
                                                                      @RequestParam List<String> color, @RequestParam List<String> size, @RequestParam Integer minPrice,
                                                                      @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
                                                                      @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        Page<Product> res = productService.getAllProduct(category, color, size,minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);

        System.out.println("Complete products");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByHandler(@PathVariable Long productId) throws ProductException{
        Product product =  productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/search")
        public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){

            List<Product> products = productService.searchProduct(q);

            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        }

    @GetMapping("/newArrival")
    public ResponseEntity<List<Product>> newArrvialProducts(){
        List<Product> products = productService.newArrivalProducts();

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/recommendation/")
    public ResponseEntity<List<Product>> recommendationForNewUser() {

        List<Product> products = recommendationService.generateRecommendationsForNewUser();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
