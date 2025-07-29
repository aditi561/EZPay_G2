package com.ezpay.bank.service;

import com.ezpay.bank.dao.UserDao;
import com.ezpay.bank.dao.UserDaoImpl;
import com.ezpay.bank.model.User;

import java.util.List;

public class userServiceImpl implements userService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registerUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public User getUser(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int userId) {
        userDao.deleteUser(userId);
    }
}
