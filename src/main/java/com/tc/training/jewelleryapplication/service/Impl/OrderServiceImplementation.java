package com.tc.training.jewelleryapplication.service.Impl;

import com.tc.training.jewelleryapplication.exception.OrderException;
import com.tc.training.jewelleryapplication.model.Address;
import com.tc.training.jewelleryapplication.model.Order;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.repository.CartRepository;
import com.tc.training.jewelleryapplication.service.CartItemService;
import com.tc.training.jewelleryapplication.service.OrderService;
import com.tc.training.jewelleryapplication.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public OrderServiceImplementation(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Order createOrder(User user, Address shippingAdress) {
        return null;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return null;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }
}
