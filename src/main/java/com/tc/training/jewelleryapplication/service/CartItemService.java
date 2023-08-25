package com.tc.training.jewelleryapplication.service;

import com.tc.training.jewelleryapplication.exception.CartItemException;
import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.Cart;
import com.tc.training.jewelleryapplication.model.CartItem;
import com.tc.training.jewelleryapplication.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException, UserException;
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
