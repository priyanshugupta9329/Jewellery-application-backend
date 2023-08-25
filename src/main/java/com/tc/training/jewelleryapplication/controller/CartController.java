package com.tc.training.jewelleryapplication.controller;


import com.tc.training.jewelleryapplication.exception.ProductException;
import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.Cart;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.request.AddItemRequest;
import com.tc.training.jewelleryapplication.response.ApiResponse;
import com.tc.training.jewelleryapplication.service.CartService;
import com.tc.training.jewelleryapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization")String jwt) throws UserException, ProductException{

        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(), req);

        ApiResponse res = new ApiResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);

        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }
}
