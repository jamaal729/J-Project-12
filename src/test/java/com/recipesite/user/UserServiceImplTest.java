package com.recipesite.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Test
    public void findOneReturnsOne() throws Exception {
        when(userRepository.findOne(1L)).thenReturn(test);

        assertThat("User Service returned one User", userService.findOne(1L).equals(test));
        verify(userRepository).findOne(1L);
    }

    @Test
    public void findByUsername() throws Exception {
        when(userRepository.findByUsername("user")).thenReturn(test);

        assertThat("User Service found User by name", userService.findByUsername("user").equals(test));
        verify(userRepository).findByUsername("user");
    }

    @Test
    public void findAll() throws Exception {
        when(userRepository.findAll()).thenReturn(testUsers);

        assertThat("User Service returned all users", userService.findAll().equals(testUsers));
        verify(userRepository).findAll();
    }

    @Test
    public void save() throws Exception {
        List<User> users = new ArrayList<>(testUsers);
        doAnswer(answer -> {
            users.add(new User());
            return true;
        }).when(userRepository).save(any(User.class));

        userService.save(new User());

        assertThat("Users increased on save", users.size() == 3);
        verify(userRepository).save(any(User.class));
    }

    private User test = new User("admin", "password", new String[]{"ROLE_ADMIN"});
    private List<User> testUsers = Arrays.asList(test, new User());
}
