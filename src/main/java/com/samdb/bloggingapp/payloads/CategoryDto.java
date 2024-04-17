package com.samdb.bloggingapp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters long")
    private String categoryTitle;

    @Size(max = 250, message = "Description must be at most 250 characters long")
    private String categoryDescription;
}
