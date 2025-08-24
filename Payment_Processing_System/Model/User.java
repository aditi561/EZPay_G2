package com.ezpay.bank.model;

import java.util.List;

/**
 * Represents a user in the EZPay banking system.
 * Each user has a unique ID, name, email ID, and a list of associated bank accounts.
 */
public class User {

    // Unique identifier for the user
    int userId;

    // Name of the user
    String userName;

    // Email address of the user
    String emailId;

    // List of bank account numbers associated with the user
    List<String> accounts;

    /**
     * Default constructor
     */
    public User() {}

    /**
     * Parameterized constructor to initialize all user fields
     * 
     * @param userId    Unique user ID
     * @param userName  Name of the user
     * @param emailId   Email ID of the user
     * @param accounts  List of associated bank account numbers
     */
    public User(int userId, String userName, String emailId, List<String> accounts) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.accounts = accounts;
    }

    // ---------------- Getters ----------------

    /**
     * Gets the user ID
     * @return userId
     */
    public int getUserID() {
        return userId;
    }

    /**
     * Gets the user name
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the email ID
     * @return emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Gets the list of account numbers
     * @return List of account numbers
     */
    public List<String> getAccounts() {
        return accounts;
    }

    // ---------------- Setters ----------------

    /**
     * Sets the user ID
     * @param userId User's unique identifier
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the user name
     * @param userName User's name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the email ID
     * @param emailId User's email address
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * Sets the list of account numbers
     * @param accounts List of user's bank account numbers
     */
    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }
}
