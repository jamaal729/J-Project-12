package com.recipesite.recipe;

import com.recipesite.category.Category;
import com.recipesite.ingredient.Ingredient;
import com.recipesite.user.User;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class RecipeTest {
    @Test
    public void recipeBuilderWorksProperly() throws Exception {
        Category category = new Category("category");
        Ingredient ingredient = new Ingredient("ingredient", "condition", 5);
        Recipe recipe = new Recipe.RecipeBuilder("recipe", category)
                .addIngredient(ingredient)
                .addInstruction("instruction", 0)
                .setCookTime(5)
                .setPrepTime(10)
                .setDescription("description")
                .setImage("image")
                .build();

        assertThat("recipe category got set", recipe.getCategory().equals(category));
        assertThat("recipe name set correctly", recipe.getName().equals("recipe"));
        assertThat("recipe ingredient was added", recipe.getIngredients().size() == 1);
        assertThat("ingredient name is correct", recipe.getIngredients().get(0).getName().equals("ingredient"));
        assertThat("ingredient condition is correct", recipe.getIngredients().get(0).getCondition().equals("condition"));
        assertThat("ingredient amount is correct", recipe.getIngredients().get(0).getQuantity() == 5);
        assertThat("recipe has one instruction", recipe.getInstructions().size() == 1);
        assertThat("recipe cook time is correct", recipe.getCookTime() == 5);
        assertThat("recipe prep time is correct", recipe.getPrepTime() == 10);
        assertThat("recipe description is 'description'", recipe.getDescription().equals("description"));
        assertThat("recipe image URL set", recipe.getImageUrl().equals("image"));
        assertThat("recipe is not favorited by user", !recipe.isFavorited(user));
    }

    private User user = new User();
}
