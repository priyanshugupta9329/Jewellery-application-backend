package com.tc.training.jewelleryapplication.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_id")
    private String orderId;

    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    @OneToOne
    private Address shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails=new PaymentDetails();

    private double totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;

    private int totalItem;
    private LocalDateTime createdAt;



}
