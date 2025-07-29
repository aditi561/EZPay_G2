package com.ezpay.bank.dao;
import com.ezpay.bank.model.User;

import java.util.*;

public class UserDaoImpl implements UserDao {
    private Map<Integer, User> userMap = new HashMap<>();

    @Override
    public void addUser(User user) {
        userMap.put(user.getUserID(), user);
    }

    @Override
    public User getUserById(int userId) {
        return userMap.get(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void updateUser(User user) {
        userMap.put(user.getUserID(), user);
    }

    @Override
    public void deleteUser(int userId) {
        userMap.remove(userId);
    }
}
