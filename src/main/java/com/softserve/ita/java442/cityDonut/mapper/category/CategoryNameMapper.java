package com.softserve.ita.java442.cityDonut.mapper.category;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryNameDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryNameMapper implements GeneralMapper<Category, CategoryNameDto> {

    @Override
    public CategoryNameDto convertToDto(Category category) {
        return CategoryNameDto.builder()
                .category(category.getCategory())
                .build();
    }

    @Override
    public Category convertToModel(CategoryNameDto categoryNameDto) {
        return Category.builder()
                .category(categoryNameDto.getCategory())
                .build();
    }

    public List<CategoryNameDto> convertListToDto(List<Category> categories) {
        List<CategoryNameDto> categoryNameDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryNameDtos.add(convertToDto(category));
        }
        return categoryNameDtos;
    }

    public List<Category> convertListToModel(List<CategoryNameDto> categoryNameDtos) {
        List<Category> categories = new ArrayList<>();
        for (CategoryNameDto categoryNameDto : categoryNameDtos) {
            categories.add(convertToModel(categoryNameDto));
        }
        return categories;
    }
}