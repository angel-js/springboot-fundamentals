package com.fundamentosplatzi.springboot.fundamentos.caseuse;

import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {
    private UserService userService;

    public void DeleteUser(UserService userService) {
        this.userService = userService;

    }

    public User save(User newUser) {
        return userService.save(newUser);
    }
}
