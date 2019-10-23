package com.softserve.ita.java442.cityDonut.mapper.category;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper implements GeneralMapper<Category,CategoryDto> {

    @Override
    public CategoryDto convertToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .category(category.getCategory())
                .build();
    }

    @Override
    public Category convertToModel(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .category(categoryDto.getCategory())
                .build();
    }

    public List<CategoryDto> convertListToDto(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(convertToDto(category));
        }
        return categoryDtos;
    }

    public List<Category> convertListToModel(List<CategoryDto> categoryDtos) {
        List<Category> categories = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtos) {
            categories.add(convertToModel(categoryDto));
        }
        return categories;
    }
}
