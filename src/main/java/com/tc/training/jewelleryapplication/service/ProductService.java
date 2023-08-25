package com.tc.training.jewelleryapplication.service;

import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.model.Product;
import com.tc.training.jewelleryapplication.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product req) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProduct(String category,List<String> colors,List<String> size,Integer minPrice,Integer maxPrice,Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);

    public List<Product> findAllProducts();
}
