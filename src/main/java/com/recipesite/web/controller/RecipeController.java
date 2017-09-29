package com.recipesite.web.controller;

import com.recipesite.category.Category;
import com.recipesite.category.CategoryService;
import com.recipesite.ingredient.IngredientService;
import com.recipesite.recipe.Recipe;
import com.recipesite.recipe.RecipeService;
import com.recipesite.user.User;
import com.recipesite.user.UserService;
import com.recipesite.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.recipesite.web.FlashMessage.Status.FAILURE;
import static com.recipesite.web.FlashMessage.Status.SUCCESS;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private UserService users;

    @RequestMapping("/")
    public String redirectToRecipesPage() {
        return "redirect:/recipes";
    }

    @RequestMapping("/recipes")
    public String index(Model model) {
        User user = (User) model.asMap().get("currentUser");
        if (user != null) {
            model.addAttribute("authenticated", user.isAdmin());
        }
        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("categories", allCategories);
        List<Recipe> allRecipes = recipeService.findAll();
        model.addAttribute("allRecipes", allRecipes);
        return "index";
    }

    @RequestMapping("/recipes/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        Recipe recipe = recipeService.findOne((long) id);
        model.addAttribute("recipe", recipe);
        return "detail";
    }

    @RequestMapping(path = "/recipes/{id}/delete", method = POST)
    public String deleteRecipe(@PathVariable("id") int id, Model model, RedirectAttributes attributes) {
        User user = (User) model.asMap().get("currentUser");
        Recipe recipe = recipeService.findOne((long) id);
        // if(recipe.isFavorited(user)) user.getFavoritedRecipes().remove(recipe);
        if (recipeService.delete(recipe, user)) {
            attributes.addFlashAttribute("flash", new FlashMessage("Recipe deleted successfully", SUCCESS));
        } else {
            attributes.addFlashAttribute("flash", new FlashMessage("You are not allowed to delete this recipe.", FAILURE));
        }
        return "redirect:/";
    }

    @RequestMapping("/search")
    public String search(Model model, RedirectAttributes attributes, @RequestParam(value = "searchQuery", required = false) String searchQuery, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "method") String method, HttpServletRequest request, HttpServletResponse response) {
        List<Recipe> queriedRecipes = new ArrayList<>();
        if (searchQuery != null) {
            model.addAttribute("search", searchQuery);

            if (method.equals("description")) {
                List<Recipe> namedRecipes = recipeService.findByDescriptionContaining(searchQuery);

                if (namedRecipes.size() < 1)
                    attributes.addFlashAttribute("flash", new FlashMessage("Search string not found!", FAILURE));

                if (!category.equals("")) {
                    List<Recipe> categorizedRecipes = recipeService.findByCategoryName(category);
                    queriedRecipes = namedRecipes.stream().filter(categorizedRecipes::contains).collect(Collectors.toList());
                } else {
                    queriedRecipes = namedRecipes;
                }
            } else if (method.equals("ingredient")) {
                List<Long> recipeIds = recipeService.findByIngredient(searchQuery);
                List<Recipe> searchedRecipes = new ArrayList<>();
                recipeIds.forEach(id -> {
                    Recipe recipe = recipeService.findOne(id);
                    searchedRecipes.add(recipe);
                });
                if (!category.equals("")) {
                    List<Recipe> categorizedRecipes = recipeService.findByCategoryName(category);
                    queriedRecipes = searchedRecipes.stream().filter(categorizedRecipes::contains).collect(Collectors.toList());
                } else {
                    queriedRecipes = searchedRecipes;
                }
            } else { // no selection

                List<Recipe> allRecipes = recipeService.findAll();
                if (!category.equals("")) {
                    List<Recipe> categorizedRecipes = recipeService.findByCategoryName(category);
                    queriedRecipes = allRecipes.stream().filter(categorizedRecipes::contains).collect(Collectors.toList());
                } else {
                    queriedRecipes = allRecipes;
                }
            }
        }
        model.addAttribute("allRecipes", queriedRecipes);
        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("categories", allCategories);
        return "index";
    }

    @RequestMapping("/recipes/add")
    public String addRecipePage(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        model.addAttribute("action", "/recipes/add");
        model.addAttribute("redirect", "/");
        model.addAttribute("categories", categoryService.findAll());
        return "edit";
    }

    @RequestMapping(path = "/recipes/add", method = POST)
    public String addRecipe(Model model, Recipe recipe, RedirectAttributes attributes) {
        Category category = recipe.getCategory();
        User user = (User) model.asMap().get("currentUser");
        if (categoryService.findByName(category.getName()) != null) {
            recipe.setCategory(categoryService.findByName(recipe.getCategory().getName()));
        } else {
            categoryService.save(category);
        }
        recipe.getIngredients().forEach(ingredient -> ingredientService.save(ingredient));
        recipe.setCreatedBy(user);
        recipeService.save(recipe, user);
        users.save(user);
        attributes.addFlashAttribute("flash", new FlashMessage("Recipe successfully created!", SUCCESS));
        return "redirect:/recipes/" + recipe.getId();
    }

    @RequestMapping("/recipes/{id}/edit")
    public String editRecipe(Model model, @PathVariable("id") int id) {
        Recipe recipe = recipeService.findOne((long) id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("action", "/recipes/" + id + "/edit");
        model.addAttribute("redirect", "/recipes/" + id);
        model.addAttribute("categories", categoryService.findAll());
        return "edit";
    }

    @RequestMapping(path = "/recipes/{id}/edit", method = POST)
    public String saveEditedRecipe(Recipe recipe, Model model, RedirectAttributes attributes) {
        Category category = recipe.getCategory();
        User user = (User) model.asMap().get("currentUser");
        if (categoryService.findByName(category.getName()) != null) {
            recipe.setCategory(categoryService.findByName(category.getName()));
        } else {
            categoryService.save(category);
        }
        recipe.getIngredients().forEach(ingredient -> ingredientService.save(ingredient));
        recipe.getInstructions()
                .stream()
                .filter(instruction -> instruction.equals(""))
                .collect(Collectors.toList())
                .forEach(recipe::removeInstruction);
        if (recipeService.save(recipe, user)) {
            attributes.addFlashAttribute("flash", new FlashMessage("Recipe edited successfully", SUCCESS));
        } else {
            attributes.addFlashAttribute("flash", new FlashMessage("You are not allowed to edit this recipe.", FAILURE));
        }
        return "redirect:/recipes/" + recipe.getId();
    }

    @RequestMapping(value = "/recipes/{id}/favorite", method = POST)
    public String toggleFavorite(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        User user = (User) model.asMap().get("currentUser");
        Recipe recipe = recipeService.findOne(id);
        String referer = request.getHeader("Referer");
        if (user.getFavoritedRecipes().contains(recipe)) {
            user.removeFavoritedRecipe(recipe);
        } else {
            user.addFavoritedRecipe(recipe);
        }
        users.save(user);
        return "redirect:" + referer;
    }
}
