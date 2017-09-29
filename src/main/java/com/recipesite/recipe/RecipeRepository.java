package com.recipesite.recipe;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigInteger;
import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    @RestResource(rel = "description-contains", path = "containsDescription")
    List<Recipe> findByDescriptionContaining(@Param("description") String description);

    @RestResource(rel = "category", path = "category")
    List<Recipe> findByCategoryName(@Param("name") String name);

    @Query(value = "SELECT DISTINCT recipe_id FROM recipe_ingredients WHERE ingredients_id = :ingredientId", nativeQuery = true)
    @RestResource(rel = "ingredientName", path = "ingredientName")
    List<BigInteger> findByIngredient(@Param("ingredientId") Long ingredientId);

    @RestResource(rel = "nameStartsWith", path = "nameStartsWith")
    List<Recipe> findByNameStartsWith(@Param("name") String name);
}
