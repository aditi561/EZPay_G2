package com.ezpay.bank.service_test;

import com.ezpay.bank.model.User;
import com.ezpay.bank.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for UserServiceImpl using only JUnit.
 */
public class UserServiceImplTest {

    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    public void testRegisterAndGetUser() {
        User user = new User(1, "Alice", "alice@example.com", new ArrayList<>());
        userService.registerUser(user);

        User fetchedUser = userService.getUser(1);
        assertNotNull(fetchedUser);
        assertEquals("Alice", fetchedUser.getUserName());
        assertEquals("alice@example.com", fetchedUser.getEmailId());
    }

    @Test
    public void testUpdateUser() {
        User user = new User(2, "Bob", "bob@example.com", new ArrayList<>());
        userService.registerUser(user);

        // Update user name
        user.setUserName("Bobby");
        userService.updateUser(user);

        User updatedUser = userService.getUser(2);
        assertEquals("Bobby", updatedUser.getUserName());
    }

    @Test
    public void testGetAllUsers() {
        userService.registerUser(new User(3, "Charlie", "charlie@example.com", new ArrayList<>()));
        userService.registerUser(new User(4, "Diana", "diana@example.com", new ArrayList<>()));

        List<User> allUsers = userService.getAllUsers();
        assertNotNull(allUsers);
        assertTrue(allUsers.size() >= 2); // Depending on existing users
    }

    @Test
    public void testNonExistentUserReturnsNull() {
        User user = userService.getUser(999);
        assertNull(user);
    }
}
