package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.CategoryDto;
import com.softserve.ita.java442.cityDonut.model.Category;
import org.modelmapper.ModelMapper;

public class CategoryMapper {
    public static CategoryDto convertToDto(Category category) {
        return new ModelMapper().map(category, CategoryDto.class);
    }

    public static Category convertToModel(CategoryDto categoryDto) {
        return new ModelMapper().map(categoryDto, Category.class);
    }
}
