package com.ezpay.bank.service;

import com.ezpay.bank.model.User;
import java.util.List;

/**
 * UserService defines the operations related to user management.
 * It acts as an abstraction over user-related business logic.
 */
public interface userService {

    /**
     * Registers a new user in the system.
     *
     * @param user The User object containing user details.
     */
    void registerUser(User user);

    /**
     * Retrieves a user by their unique user ID.
     *
     * @param userId The ID of the user to be retrieved.
     * @return The User object if found, otherwise null.
     */
    User getUser(int userId);

    /**
     * Retrieves all users registered in the system.
     *
     * @return A List of all User objects.
     */
    List<User> getAllUsers();

    /**
     * Updates the details of an existing user.
     *
     * @param user The User object with updated details.
     */
    void updateUser(User user);

    /**
     * Deletes a user by their user ID.
     *
     * @param userId The ID of the user to be deleted.
     */
    void deleteUser(int userId);
}
