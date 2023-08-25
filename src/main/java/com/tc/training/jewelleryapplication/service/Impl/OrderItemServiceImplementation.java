package com.tc.training.jewelleryapplication.service.Impl;

import com.tc.training.jewelleryapplication.model.OrderItem;
import com.tc.training.jewelleryapplication.repository.OrderItemRepository;
import com.tc.training.jewelleryapplication.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderItemServiceImplementation implements OrderItemService {


    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
