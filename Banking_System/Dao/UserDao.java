package com.ezpay.bank.dao;

import com.ezpay.bank.model.User;
import java.util.List;

/**
 * Interface representing the data access operations related to User.
 * Acts as a contract for UserDaoImpl to implement the necessary CRUD functionalities.
 */
public interface UserDao {

    /**
     * Adds a new user to the system.
     * 
     * @param user The User object to be added.
     */
    void addUser(User user);

    /**
     * Retrieves a user by their unique user ID.
     * 
     * @param userId The ID of the user to retrieve.
     * @return The User object corresponding to the given ID, or null if not found.
     */
    User getUserById(int userId);

    /**
     * Retrieves a list of all users in the system.
     * 
     * @return A list containing all User objects.
     */
    List<User> getAllUsers();

    /**
     * Updates the details of an existing user.
     * 
     * @param user The User object with updated details.
     */
    void updateUser(User user);

    /**
     * Deletes a user from the system using their user ID.
     * 
     * @param userId The ID of the user to be deleted.
     */
    void deleteUser(int userId);
}
