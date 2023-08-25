package com.tc.training.jewelleryapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    @Column(name="cart_items")
    private Set<CartItem> cartItems=new HashSet<>();

    @Column(name="total_price")
    private double totalPrice;

    @Column(name="total_item")
    private int totalItem;
    private int totalDiscountPrice;
    private int discount;

    @Override
    public String toString() {
        return "Cart(id=" + id + ", user=" + user.getId() +
                ", totalPrice=" + totalPrice + ", totalItem=" + totalItem +
                ", totalDiscountPrice=" + totalDiscountPrice + ", discount=" + discount + ")";
    }

}
