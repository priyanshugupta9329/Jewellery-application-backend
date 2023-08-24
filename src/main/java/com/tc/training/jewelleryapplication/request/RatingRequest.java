package com.tc.training.jewelleryapplication.request;

public class RatingRequest {

    private Long producrId;
    private double rating;


    public Long getProducrId() {
        return producrId;
    }

    public void setProducrId(Long producrId) {
        this.producrId = producrId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
