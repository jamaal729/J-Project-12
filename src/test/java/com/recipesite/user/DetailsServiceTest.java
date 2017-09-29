package com.recipesite.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetailsServiceTest {
    @Mock
    private UserService users;

    @InjectMocks
    private DetailsService service;

    @Test(expected = UsernameNotFoundException.class)
    public void notFoundThrowsException() throws Exception {
        service.loadUserByUsername("someUser");
    }

    @Test
    public void foundUsernameCreatesCredentials() throws Exception {
        when(users.findByUsername("user")).thenReturn(user);

        assertThat(service.loadUserByUsername("user").getUsername(), is("user"));
    }

    private User user = new User("user", "password", new String[]{"ROLE_ADMIN"});
}
