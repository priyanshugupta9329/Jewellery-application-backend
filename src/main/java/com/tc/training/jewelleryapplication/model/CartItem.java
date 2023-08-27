package com.tc.training.jewelleryapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;

    private String size;

    private int quantity;
    private Integer price;
    private Integer discountPrice;
    private Long userId;

    @Override
    public String toString() {
        return "CartItem(id=" + id + ", product=" + product.getId() +
                ", size=" + size + ", quantity=" + quantity +
                ", price=" + price + ", discountPrice=" + discountPrice + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, product, size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItem other = (CartItem) obj;
        return Objects.equals(id, other.id) && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
                && Objects.equals(product, other.product) && Objects.equals(size, other.size);
    }



}
