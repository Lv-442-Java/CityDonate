package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoriesByIds(List<Long> categoryIds);
}
