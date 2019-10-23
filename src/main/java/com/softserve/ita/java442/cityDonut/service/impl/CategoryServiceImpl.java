package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.model.Category;
import com.softserve.ita.java442.cityDonut.repository.CategoryRepository;
import com.softserve.ita.java442.cityDonut.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category getCategoryByCategory(String category) {
        return categoryRepository.getCategoryByCategory(category);
    }

    @Override
    public List<Category> getCategoriesByCategories(List<String> categoryNames) {
        List<Category> requiredCategories = new ArrayList<>();
        for (String categoryName : categoryNames) {
            requiredCategories.add(getCategoryByCategory(categoryName));
        }
        return requiredCategories;
    }
}
