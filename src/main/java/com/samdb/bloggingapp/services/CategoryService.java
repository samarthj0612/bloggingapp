package com.samdb.bloggingapp.services;

import com.samdb.bloggingapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    CategoryDto getCategoryById(Integer categoryId);

    CategoryDto deleteCategory(Integer categoryId);
}
