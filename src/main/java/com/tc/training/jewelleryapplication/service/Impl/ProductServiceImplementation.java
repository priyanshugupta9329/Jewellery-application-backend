package com.tc.training.jewelleryapplication.service.Impl;

import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.model.CartItem;
import com.tc.training.jewelleryapplication.model.Category;
import com.tc.training.jewelleryapplication.model.Order;
import com.tc.training.jewelleryapplication.model.Product;
import com.tc.training.jewelleryapplication.repository.CategoryRepository;
import com.tc.training.jewelleryapplication.repository.ProductRepository;
import com.tc.training.jewelleryapplication.request.CreateProductRequest;
import com.tc.training.jewelleryapplication.service.OrderService;
import com.tc.training.jewelleryapplication.service.ProductService;
import com.tc.training.jewelleryapplication.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {
    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;



    public ProductServiceImplementation(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;

    }

    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel=categoryRepository.findByName(req.getTopLevelCategory());
        if(topLevel==null)
        {
            Category topLevelCategory=new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel=categoryRepository.save(topLevelCategory);
        }
        Category secondLevel=categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
        if(secondLevel==null)
        {
            Category secondLevelCategory=new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel=categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel=categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
        if(thirdLevel==null)
        {
            Category thirdLevelCategory=new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel=categoryRepository.save(thirdLevelCategory);
        }

        Product product=new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        product.setImageUrl2(req.getImageUrl2());
        product.setImageUrl3(req.getImageUrl3());
        product.setImageUrl4(req.getImageUrl4());
        product.setOccasion(req.getOccasion());
        product.setMaterial(req.getMaterial());
        product.setDimension(req.getDimension());
        product.setWeight(req.getWeight());
        product.setCollection(req.getCollection());
        product.setCareLabel(req.getCareLabel());

        Product savedProduct=productRepository.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product=findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product=findProductById(productId);
        if(req.getQuantity()!=0)
        {
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt=productRepository.findById(id);

        if(opt.isPresent())
        {
            return opt.get();
        }
        throw new ProductException("Product not found with id - "+id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {

        List<Product> products = productRepository.findByCategoryName(category);
        return products;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> size, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize);

        List<Product> products=productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);
        if(!colors.isEmpty())
        {
            products=products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }
        if(stock!=null)
        {
            if(stock.equals("in_stock"))
            {
                products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }
            else if(stock.equals("out_of_stock"))
            {
                products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }
        int startIndex=(int)pageable.getOffset();
        int endIndex=Math.min(startIndex + pageable.getPageSize(),products.size());

        List<Product> pageContent=products.subList(startIndex,endIndex);

        Page<Product> filteredProduct=new PageImpl<>(pageContent,pageable, products.size());
        return filteredProduct;
    }

    @Override
    public List<Product> findAllProducts() {

        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public List<Product> recommendProductsByCategory(String category) {

        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> searchProduct(String query) {
        List<Product> products=productRepository.searchProduct(query);
        return products;
    }

    @Override
    public List<Product> newArrivalProducts() {
        List<Product> products=productRepository.sortByCreatedAt();
        return products;
    }


}
