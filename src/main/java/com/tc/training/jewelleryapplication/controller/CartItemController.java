package com.tc.training.jewelleryapplication.controller;

import com.tc.training.jewelleryapplication.exception.CartItemException;
import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.CartItem;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.request.AddItemRequest;
import com.tc.training.jewelleryapplication.response.ApiResponse;
import com.tc.training.jewelleryapplication.service.CartItemService;
import com.tc.training.jewelleryapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt) throws UserException, CartItemException {

        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("item deleted from cart");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem,@PathVariable Long cartItemId,@RequestHeader("Authorization")String jwt) throws UserException, CartItemException{

        User user = userService.findUserProfileByJwt(jwt);

        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }
}
