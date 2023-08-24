package com.tc.training.jewelleryapplication.repository;

import com.tc.training.jewelleryapplication.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r From Review r Where r.product.id=:productId")
    public List<Review> getAllProductsReview(@Param("productId") Long productId);
}
