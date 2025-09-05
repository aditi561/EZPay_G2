package com.ezpay.bank.controller;

import com.ezpay.bank.model.User;
import com.ezpay.bank.service.UserService;
import com.ezpay.bank.service.UserServiceImpl;

import java.util.List;

/**
 * Controller class to handle user-related operations.
 */
public class UserController {

    private final UserService userService = new UserServiceImpl();

    /**
     * Registers a new user.
     *
     * @param user The user to register.
     */
    public void registerUser(User user) {
        userService.registerUser(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The User object, or null if not found.
     */
    public User getUser(int userId) {
        return userService.getUser(userId);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of User objects.
     */
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Updates user details.
     *
     * @param user The user object with updated data.
     */
    public void updateUser(User user) {
        userService.updateUser(user);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     */
    public void deleteUser(int userId) {
        userService.deleteUser(userId);
    }
}
