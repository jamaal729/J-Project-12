package com.recipesite.base;

import com.recipesite.Application;
import com.recipesite.category.Category;
import com.recipesite.category.CategoryService;
import com.recipesite.ingredient.Ingredient;
import com.recipesite.ingredient.IngredientService;
import com.recipesite.recipe.Recipe;
import com.recipesite.recipe.RecipeRepository;
import com.recipesite.user.User;
import com.recipesite.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private UserService users;
    private IngredientService ingredients;
    private RecipeRepository recipes;
    private CategoryService categories;

    @Autowired
    public DatabaseLoader(UserService users, IngredientService ingredients, RecipeRepository recipes, CategoryService categories) {
        this.users = users;
        this.ingredients = ingredients;
        this.recipes = recipes;
        this.categories = categories;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if ((users.findAll()).isEmpty()) {
            User admin = new User("admin", "password", new String[]{"ROLE_ADMIN", "ROLE_USER"});
            users.save(admin);
            User user1 = new User("user1", "pass1", new String[]{"ROLE_USER"});
            users.save(user1);
            User user2 = new User("user2", "pass2", new String[]{"ROLE_USER"});
            users.save(user2);

            // Category 1
            Category breakfastAndBrunch = new Category("Breakfast and Brunch");
            categories.save(breakfastAndBrunch);

            Ingredient tartApples = new Ingredient("tart apples", "", 2);
            ingredients.save(tartApples);
            Ingredient whiteSugar = new Ingredient("white sugar", "cup", (int) 1);
            ingredients.save(whiteSugar);
            Ingredient groundCinnamon = new Ingredient("ground cinnamon", "tablespoon", 1);
            ingredients.save(groundCinnamon);

            Recipe recipe1 = new Recipe.RecipeBuilder("Cinnamon Apple Syrup", breakfastAndBrunch)
                    .setDescription("Apples simmered with sugar and cinnamon")
                    .addIngredient(tartApples)
                    .addIngredient(whiteSugar)
                    .addIngredient(groundCinnamon)
                    .addInstruction("In a saucepan, combine the apples, sugar, cinnamon and water.", 0)
                    .addInstruction("Stir to dissolve sugar, and bring to a boil.", 1)
                    .setPrepTime(5)
                    .setCookTime(15)
                    .setImage("http://images.media-allrecipes.com/userphotos/250x250/385183.jpg")
                    .build();
            recipe1.setCreatedBy(user1);
            recipes.save(recipe1);

            // Category 2
            Category dinner = new Category("Dinner");
            categories.save(dinner);

            Ingredient beefSteakCuts = new Ingredient("beef round steak cuts", "pound", 1);
            ingredients.save(beefSteakCuts);
            Ingredient snowPeas = new Ingredient("snow peas", "ounces", 8);
            ingredients.save(snowPeas);
            Ingredient gingerRoot = new Ingredient("minced fresh ginger root", "tablespoon", 1);
            ingredients.save(gingerRoot);

            Recipe recipe2 = new Recipe.RecipeBuilder("Asian Beef with Snow Peas", dinner)
                    .setDescription("Stir-fried beef in a light gingery sauce")
                    .addIngredient(beefSteakCuts)
                    .addIngredient(snowPeas)
                    .addIngredient(gingerRoot)
                    .addInstruction("Heat oil in a wok or skillet over medium high heat.", 0)
                    .addInstruction("Stir-fry ginger and garlic for 30 seconds.", 1)
                    .addInstruction("Add the steak and stir-fry for 2 minutes.", 2)
                    .addInstruction("Add the snow peas and stir-fry for an additional 3 minutes.", 3)
                    .addInstruction("Add the soy sauce mixture, bring to a boil, stirring constantly.", 4)
                    .addInstruction("Lower heat and simmer until the sauce is thick and smooth.", 5)
                    .setPrepTime(5)
                    .setCookTime(10)
                    .setImage("http://images.media-allrecipes.com/userphotos/560x315/971360.jpg")
                    .build();
            recipe2.setCreatedBy(user2);
            recipes.save(recipe2);

            // Category 3
            Category drinks = new Category("Drinks");
            categories.save(drinks);

            Ingredient boilingWater = new Ingredient("boiling water", "gallon", 1);
            ingredients.save(boilingWater);
            Ingredient blackTeaBags = new Ingredient("black tea bags", "bags", 6);
            ingredients.save(blackTeaBags);
            Ingredient brownSugar = new Ingredient("brown sugar", "cups", 2);
            ingredients.save(brownSugar);
            Ingredient juicedLimes = new Ingredient("juiced limes", "-", 4);
            ingredients.save(juicedLimes);

            Recipe recipe3 = new Recipe.RecipeBuilder("Sweet Lime Iced Tea", drinks)
                    .setDescription("Not too sweet, and not too bitter... and the fresh lime juice")
                    .addIngredient(boilingWater)
                    .addIngredient(blackTeaBags)
                    .addIngredient(brownSugar)
                    .addIngredient(juicedLimes)
                    .addInstruction("Pour the water into a gallon sized jar over the tea bags.", 0)
                    .addInstruction("Allow to steep for 45 minutes.", 1)
                    .addInstruction("Remove and discard the tea bags.", 2)
                    .addInstruction("Stir in the sugar and lime juice until the sugar has dissolved.", 3)
                    .addInstruction("Cool to room temperature;", 4)
                    .addInstruction("Refrigerate until cold before serving.", 5)
                    .setPrepTime(10)
                    .setCookTime(5)
                    .setImage("http://images.media-allrecipes.com/userphotos/600x600/258601.jpg")
                    .build();
            recipe3.setCreatedBy(admin);
            recipes.save(recipe3);
        } else {
            Application.main(args.getSourceArgs());
        }
    }
}
