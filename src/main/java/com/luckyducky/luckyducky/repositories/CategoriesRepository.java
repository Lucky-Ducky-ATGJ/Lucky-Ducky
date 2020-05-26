package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
