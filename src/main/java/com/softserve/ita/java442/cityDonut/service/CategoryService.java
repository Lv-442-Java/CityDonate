package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.category.CategoryDto;
import com.softserve.ita.java442.cityDonut.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoriesByIds(List<String> categoryIds);

    List<CategoryDto> getAllCategories();
}
