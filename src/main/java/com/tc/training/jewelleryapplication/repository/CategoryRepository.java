package com.tc.training.jewelleryapplication.repository;

import com.tc.training.jewelleryapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    public Category findByName(String name);
    @Query("SELECT c FROM Category c Where c.name=:name And c.parentCategory.name=:parentCategoryName")
    public Category findByNameAndParent(@Param("name")String name,@Param("parentCategoryName")String parentCategoryName);
}
