package com.ezpay.bank.dao;

import com.ezpay.bank.model.User;
import java.util.*;

/**
 * Implementation of the UserDao interface using an in-memory data store.
 * This class manages User data using a HashMap with userId as the key.
 */
public class UserDaoImpl implements UserDao {

    // In-memory storage of users, keyed by userId
    private Map<Integer, User> userMap = new HashMap<>();

    /**
     * Adds a new user to the in-memory store.
     * If the user already exists (same userId), it will overwrite the existing one.
     *
     * @param user The User object to be added.
     */
    @Override
    public void addUser(User user) {
        userMap.put(user.getUserID(), user);
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The User object if found; otherwise, null.
     */
    @Override
    public User getUserById(int userId) {
        return userMap.get(userId);
    }

    /**
     * Returns a list of all users in the system.
     *
     * @return A list of all stored User objects.
     */
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    /**
     * Updates an existing user's information.
     * If the user does not exist, this will add the user as a new entry.
     *
     * @param user The User object with updated information.
     */
    @Override
    public void updateUser(User user) {
        userMap.put(user.getUserID(), user);
    }

    /**
     * Deletes a user from the store based on their ID.
     *
     * @param userId The ID of the user to be removed.
     */
    @Override
    public void deleteUser(int userId) {
        userMap.remove(userId);
    }
}
