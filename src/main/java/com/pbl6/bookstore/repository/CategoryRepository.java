package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Category findByName(String name);

}
