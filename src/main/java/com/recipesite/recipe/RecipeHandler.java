package com.recipesite.recipe;

import com.recipesite.user.User;
import com.recipesite.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Recipe.class)
public class RecipeHandler {
    private final UserService users;

    @Autowired
    public RecipeHandler(UserService users) {
        this.users = users;
    }

    @HandleBeforeSave
    @HandleBeforeCreate
    public void setCreatorOnCreate(Recipe recipe) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = users.findByUsername(username);
        recipe.setCreatedBy(user);
    }
}
