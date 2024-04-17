package com.samdb.bloggingapp.controllers;

import com.samdb.bloggingapp.payloads.CategoryDto;
import com.samdb.bloggingapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = this.categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String Id) {
        Integer categoryId = Integer.parseInt(Id);
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String Id) {
        Integer categoryId = Integer.parseInt(Id);
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<CategoryDto> deleteCategoryById(@PathVariable String Id) {
        Integer categoryId = Integer.parseInt(Id);
        CategoryDto categoryDto = this.categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

}
