package com.recipesite.web.controller;

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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService users;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setViewResolvers(viewResolver).build();
    }

    @Test
    public void profileLoadsCorrectViewWithCurrentUser() throws Exception {
        mockMvc.perform(get("/profile"))

                //Current User will be null based on way handler is set up
                .andExpect(model().attribute("user", nullValue()))
                .andExpect(view().name("profile"));
    }

    @Test
    public void userPageLoadsCorrectViewWithCorrectUser() throws Exception {
        when(users.findOne(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))

                //Current User will be null based on way handler is set up
                .andExpect(model().attribute("user", is(user)))
                .andExpect(view().name("profile"));
    }

    private User user = new User("user", "password", new String[]{"ROLE_ADMIN"});
}
