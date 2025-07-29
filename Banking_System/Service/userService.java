package com.ezpay.bank.service;

import com.ezpay.bank.model.User;
import java.util.List;

public interface userService {
    void registerUser(User user);
    User getUser(int userId);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int userId);
}
