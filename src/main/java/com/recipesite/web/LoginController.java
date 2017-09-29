package com.recipesite.web;

import com.recipesite.user.User;
import com.recipesite.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.recipesite.web.FlashMessage.Status.FAILURE;
import static com.recipesite.web.FlashMessage.Status.SUCCESS;

@Controller
public class LoginController {
    @Autowired
    private UserService users;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUser(User user, RedirectAttributes attributes) {
        if (users.findByUsername(user.getUsername()) == null) {
            user.setRoles(new String[]{"ROLE_USER"});
            users.save(user);
            attributes.addFlashAttribute("flash", new FlashMessage(String.format("User %s successfully created! Please log in", user.getUsername()), SUCCESS));
            return "redirect:/login";
        } else {
            attributes.addFlashAttribute("flash", new FlashMessage(String.format("Account with username '%s' already exists. " +
                    "Please enter a different username.", user.getUsername()), FAILURE));
            return "redirect:/signup";
        }
    }
}
