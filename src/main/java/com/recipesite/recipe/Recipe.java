package com.recipesite.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipesite.base.BaseEntity;
import com.recipesite.category.Category;
import com.recipesite.ingredient.Ingredient;
import com.recipesite.user.User;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends BaseEntity {
    private String name;
    private String description;

    @ManyToOne
    private Category category;

    @OneToMany
    private List<Ingredient> ingredients;

    @ElementCollection
    private List<String> instructions;

    private int prepTime;
    private int cookTime;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    @JsonIgnore
    private User createdBy;
    private String imageUrl;

    public Recipe() {
        super();
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public Recipe(RecipeBuilder builder) {
        this();
        this.name = builder.name;
        this.description = builder.description;
        this.category = builder.category;
        this.ingredients = builder.ingredients;
        this.instructions = builder.instructions;
        this.prepTime = builder.prepTime;
        this.cookTime = builder.cookTime;
        this.imageUrl = builder.imageUrl;
    }

    public static class RecipeBuilder {
        private String name;
        private String description;
        private Category category;
        private List<Ingredient> ingredients;
        private List<String> instructions;
        private int prepTime;
        private int cookTime;
        private String imageUrl;

        public RecipeBuilder(String name, Category category) {
            this.name = name;
            this.category = category;
            ingredients = new ArrayList<>();
            instructions = new ArrayList<>();
        }

        public RecipeBuilder addIngredient(Ingredient ingredient) {
            ingredients.add(ingredient);
            return this;
        }

        public RecipeBuilder addInstruction(String instruction, int step) {
            instructions.add(step, instruction);
            return this;
        }

        public RecipeBuilder setPrepTime(int prepTime) {
            this.prepTime = prepTime;
            return this;
        }

        public RecipeBuilder setCookTime(int cookTime) {
            this.cookTime = cookTime;
            return this;
        }

        public RecipeBuilder setImage(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public RecipeBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Recipe build() {
            return new Recipe(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void removeInstruction(String instruction) {
        instructions.remove(instruction);
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        if (createdBy != null) {
            createdBy.addCreatedRecipe(this);
        }
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavorited(User user) {
        if (user.getFavoritedRecipes().contains(this)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (prepTime != recipe.prepTime) return false;
        if (cookTime != recipe.cookTime) return false;
        if (!name.equals(recipe.name)) return false;
        if (description != null ? !description.equals(recipe.description) : recipe.description != null) return false;
        if (category != null ? !category.equals(recipe.category) : recipe.category != null) return false;
        if (ingredients != null ? !ingredients.equals(recipe.ingredients) : recipe.ingredients != null) return false;
        if (instructions != null ? !instructions.equals(recipe.instructions) : recipe.instructions != null)
            return false;
        if (createdBy != null ? !createdBy.equals(recipe.createdBy) : recipe.createdBy != null) return false;
        return imageUrl != null ? imageUrl.equals(recipe.imageUrl) : recipe.imageUrl == null;
    }
}
