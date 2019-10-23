package com.softserve.ita.java442.cityDonut.mapper.category;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static CategoryDto convertToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .category(category.getCategory())
                .build();
    }

    public static Category convertToModel(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .category(categoryDto.getCategory())
                .build();
    }

    public static List<CategoryDto> convertListToDto(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(convertToDto(category));
        }
        return categoryDtos;
    }

    public static List<Category> convertListToModel(List<CategoryDto> categoryDtos) {
        List<Category> categories = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtos) {
            categories.add(convertToModel(categoryDto));
        }
        return categories;
    }
}
