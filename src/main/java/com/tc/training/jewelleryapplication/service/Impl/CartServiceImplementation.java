package com.tc.training.jewelleryapplication.service.Impl;

import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.model.Cart;
import com.tc.training.jewelleryapplication.model.CartItem;
import com.tc.training.jewelleryapplication.model.Product;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.repository.CartItemRepository;
import com.tc.training.jewelleryapplication.repository.CartRepository;
import com.tc.training.jewelleryapplication.request.AddItemRequest;
import com.tc.training.jewelleryapplication.service.CartItemService;
import com.tc.training.jewelleryapplication.service.CartService;
import com.tc.training.jewelleryapplication.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartServiceImplementation implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;

    private ProductService productService;

    public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {

        Cart cart = cartRepository.findByUserId(userId);

        Product product = productService.findProductById(req.getProductId());

        CartItem isPresent=cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
        if(isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price =  req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);

            cart.getCartItems().add(createdCartItem);
            cartRepository.save(cart);

        }

        return "Item Add to Cart";
    }




    @Override
    public Cart findUserCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId);
        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for(CartItem cartItem : cart.getCartItems()){
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalDiscountPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(cart.getCartItems().size());
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        cart.setTotalItem(totalItem);

        return cartRepository.save(cart);
    }
}
