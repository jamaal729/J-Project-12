package com.recipesite.category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findOne(Long id);
    Category findByName(String name);
    void save(Category category);
}
