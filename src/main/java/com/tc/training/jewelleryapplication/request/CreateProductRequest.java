package com.tc.training.jewelleryapplication.request;

import com.tc.training.jewelleryapplication.model.Size;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int discountPercent;
    private int quantity;
    private String brand;

    private String color;
    private Set<Size> size=new HashSet<>();

    private String imageUrl;
    private String topLevelCategory;

    private String secondLevelCategory;

    private String thirdLevelCategory;


    private String imageUrl2;


    private String imageUrl3;


    private String imageUrl4;

    private String occasion;

    private String material;

    private String dimension;

    private String weight;

    private String collection;

    private String careLabel;

    }
