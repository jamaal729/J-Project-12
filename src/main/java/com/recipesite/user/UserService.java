package com.recipesite.user;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findOne(Long id);
    User findByUsername(String username);
    void save(User user);
}
