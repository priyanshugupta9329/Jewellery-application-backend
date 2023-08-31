package com.tc.training.jewelleryapplication.request;

import com.tc.training.jewelleryapplication.model.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;

    private String dimensions;
    private String material;
    private String weight;
    private String occasion;
    private List<String> features=new ArrayList<>();
    private String topLevelCategory;

    private String secondLevelCategory;

    private String thirdLevelCategory;

}
