package com.recipesite.web;

import com.recipesite.user.User;
import com.recipesite.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static com.recipesite.web.FlashMessage.Status.FAILURE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserService users;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setViewResolvers(viewResolver).build();
    }

    @Test
    public void loginLoadsCorrectView() throws Exception {
        mockMvc.perform(get("/login"))

                .andExpect(view().name("login"));
    }

    @Test
    public void signupLoadsCorrectView() throws Exception {
        mockMvc.perform(get("/signup"))

                .andExpect(view().name("signup"));
    }

    @Test
    public void canSignup() throws Exception {
        when(users.findByUsername(any(String.class))).thenReturn(null);

        mockMvc.perform(post("/newUser"))

                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attribute("flash", hasProperty("message", equalTo("User null successfully created! Please log in"))));
    }

    @Test
    public void cannotDuplicateUsernames() throws Exception {
        when(users.findByUsername(any(String.class))).thenReturn(user);

        mockMvc.perform(post("/newUser"))

                .andExpect(redirectedUrl("/signup"))
                .andExpect(flash().attribute("flash", hasProperty("status", equalTo(FAILURE))));
    }

    private User user = new User("user", "password", new String[]{"ROLE_ADMIN"});
}
