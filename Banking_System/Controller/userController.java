package com.ezpay.bank.controller;
import com.ezpay.bank.model.User;
import com.ezpay.bank.service.userService;
import com.ezpay.bank.service.userServiceImpl;
import java.util.List;

public class userController {
    private userService userService = new userServiceImpl();

    public void registerUser(User user) {
        userService.registerUser(user);
    }

    public User getUser(int userId) {
        return userService.getUser(userId);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }

    public void deleteUser(int userId) {
        userService.deleteUser(userId);
    }
}