package com.samdb.bloggingapp.services.impl;

import com.samdb.bloggingapp.entities.Category;
import com.samdb.bloggingapp.exceptions.ResourceNotFound;
import com.samdb.bloggingapp.payloads.CategoryDto;
import com.samdb.bloggingapp.repositories.CategoryRepo;
import com.samdb.bloggingapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos;
        categoryDtos = categories.stream().map((category) -> this.categoryToCategoryDto(category)).collect(Collectors.toList());

        return categoryDtos;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.categoryDtoToCategory(categoryDto);
        category = this.categoryRepo.save(category);

        return this.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("CategoryId", "Id", categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = this.categoryRepo.save(category);
        CategoryDto updatedCategoryDto;
        updatedCategoryDto = this.categoryToCategoryDto(updatedCategory);

        return updatedCategoryDto;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("CategoryId", "Id", categoryId));

        CategoryDto categoryDto;
        categoryDto = this.categoryToCategoryDto(category);

        return categoryDto;
    }

    @Override
    public CategoryDto deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("CategoryId", "Id", categoryId));

        this.categoryRepo.deleteById(categoryId);
        CategoryDto deletedCategoryDto;
        deletedCategoryDto = this.categoryToCategoryDto(category);

        return deletedCategoryDto;
    }

    private CategoryDto categoryToCategoryDto(Category category) {
        CategoryDto categoryDto;
        categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    private Category categoryDtoToCategory(CategoryDto categoryDto) {
        Category category;
        category = this.modelMapper.map(categoryDto, Category.class);
        return category;
    }
}
