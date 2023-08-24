package com.tc.training.jewelleryapplication.service;

import com.tc.training.jewelleryapplication.exception.OrderException;
import com.tc.training.jewelleryapplication.model.Address;
import com.tc.training.jewelleryapplication.model.Order;
import com.tc.training.jewelleryapplication.model.User;
import org.hibernate.metamodel.mapping.ordering.ast.OrderingExpression;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user, Address shippingAdress);
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> usersOrderHistory(Long userId);
    public Order placedOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order cancledOrder(Long orderId) throws OrderException;

    public void deleteOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

}
