package com.samdb.bloggingapp.repositories;

import com.samdb.bloggingapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
