package com.ezpay.bank.service;

import com.ezpay.bank.dao.UserDao;
import com.ezpay.bank.dao.UserDaoImpl;
import com.ezpay.bank.model.User;
import java.util.List;

/**
 * Implementation of the UserService interface.
 * Provides business logic for managing user operations such as registration, retrieval, update, and deletion.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     * Registers a new user by delegating to the UserDao.
     *
     * @param user The User object containing user details.
     */
    @Override
    public void registerUser(User user) {
        userDao.addUser(user);
    }

    /**
     * Retrieves a user by their unique user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The User object if found; otherwise, null.
     */
    @Override
    public User getUser(int userId) {
        return userDao.getUserById(userId);
    }

    /**
     * Retrieves a list of all registered users.
     *
     * @return A List of User objects.
     */
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    /**
     * Updates an existing user's details.
     *
     * @param user The User object with updated information.
     */
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    /**
     * Deletes a user by their unique user ID.
     *
     * @param userId The ID of the user to delete.
     */
    @Override
    public void deleteUser(int userId) {
        userDao.deleteUser(userId);
    }
}
