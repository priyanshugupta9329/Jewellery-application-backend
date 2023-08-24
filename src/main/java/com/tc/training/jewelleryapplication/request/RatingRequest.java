package com.tc.training.jewelleryapplication.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RatingRequest {

    private Long producrId;
    private double rating;


}
