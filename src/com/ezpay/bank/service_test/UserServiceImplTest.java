package com.ezpay.bank.service_test;

import com.ezpay.bank.model.User;
import com.ezpay.bank.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UPIPaymentDAO interface using UPIPaymentDAOImpl.
 * 
 * @author: Muskan  
 * @version: 0.0.1
 * Date: 2025-08-01
 */
public class UserServiceImplTest {

    private UserServiceImpl userService;

    /**
     * Initializes a new instance of UserServiceImpl before each test case.
     */
    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl();
    }

    /**
     * Tests the user registration and retrieval functionality.
     * Ensures that a registered user can be correctly fetched by ID.
     */
    @Test
    public void testRegisterAndGetUser() {
        // Create a new user
        User user = new User(1, "Alice", "alice@example.com", new ArrayList<>());

        // Register the user
        userService.registerUser(user);

        // Fetch the registered user
        User fetchedUser = userService.getUser(1);

        // Validate the fetched user's details
        assertNotNull(fetchedUser, "User should be found after registration");
        assertEquals("Alice", fetchedUser.getUserName(), "User name should match");
        assertEquals("alice@example.com", fetchedUser.getEmailId(), "Email ID should match");
    }

    /**
     * Tests updating a user's details.
     * Verifies that changes to a user's name are saved correctly.
     */
    @Test
    public void testUpdateUser() {
        // Register a new user
        User user = new User(2, "Bob", "bob@example.com", new ArrayList<>());
        userService.registerUser(user);

        // Update the user's name
        user.setUserName("Bobby");
        userService.updateUser(user);

        // Fetch and validate the updated user
        User updatedUser = userService.getUser(2);
        assertEquals("Bobby", updatedUser.getUserName(), "Updated user name should be saved");
    }

    /**
     * Tests retrieval of all registered users.
     * Ensures that the list of users includes the ones just registered.
     */
    @Test
    public void testGetAllUsers() {
        // Register multiple users
        userService.registerUser(new User(3, "Charlie", "charlie@example.com", new ArrayList<>()));
        userService.registerUser(new User(4, "Diana", "diana@example.com", new ArrayList<>()));

        // Fetch all users
        List<User> allUsers = userService.getAllUsers();

        // Validate the user list
        assertNotNull(allUsers, "User list should not be null");
        assertTrue(allUsers.size() >= 2, "User list should contain at least two users");
    }

    /**
     * Tests retrieval of a user that doesn't exist.
     * Ensures that requesting a non-existent user ID returns null.
     */
    @Test
    public void testNonExistentUserReturnsNull() {
        // Attempt to fetch a non-existent user
        User user = userService.getUser(999);

        // Validate that null is returned
        assertNull(user, "Non-existent user should return null");
    }
}
