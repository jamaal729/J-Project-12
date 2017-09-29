package com.recipesite.ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findByName(String name);
    void save(Ingredient ingredient);
}
