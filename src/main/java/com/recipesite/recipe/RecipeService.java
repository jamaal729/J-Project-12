package com.recipesite.recipe;

import com.recipesite.user.User;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAll();
    Recipe findOne(Long id);
    List<Recipe> findByCategoryName(String category);
    List<Recipe> findByDescriptionContaining(String searchQuery);
    List<Long> findByIngredient(String searchQuery);
    List<Recipe> searchByIngredient(String searchQuery);
    List<Recipe> findByNameStartsWith(String searchQuery);
    boolean save(Recipe recipe, User user);
    boolean delete(Recipe recipe, User user);
    boolean delete(Long id, User user);
}
