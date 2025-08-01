package com.ezpay.bank.model;

import java.util.*;

/**
 * Represents a bank account associated with a user in the EZPay banking system.
 */
public class BankAccount {
    // Unique identifier for the bank account
    private int bankid;

    // Name of the bank (e.g., SBI, HDFC, ICICI)
    private String bankName;

    // Account number associated with the bank
    private String accountNumber;

    // Status flag to check if the account is verified
    private boolean isVerified;
    
 // Add this field
    private double balance = 0.0;

    
    /**
     * Default constructor
     */
    public BankAccount() {}

    /**
     * Constructor to initialize a bank account object with given details.
     * 
     * @param bankid         Unique bank ID
     * @param bankName       Name of the bank
     * @param accountNumber  Account number
     * @param isVerified     Verification status
     */
    public BankAccount(int bankid, String bankName, String accountNumber, boolean isVerified) {
        super();
        this.bankid = bankid;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.isVerified = isVerified;
    }

    // Getter methods

    /**
     * Gets the unique ID of the bank.
     * 
     * @return Bank ID
     */
    public int getBankid() {
        return bankid;
    }

    /**
     * Gets the name of the bank.
     * 
     * @return Bank name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Gets the account number.
     * 
     * @return Account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Checks whether the bank account is verified.
     * 
     * @return true if verified, false otherwise
     */
    public boolean isVerified() {
        return isVerified;
    }

    // Setter methods

    /**
     * Sets the bank ID.
     * 
     * @param bankid Bank ID
     */
    public void setBankid(int bankid) {
        this.bankid = bankid;
    }

    /**
     * Sets the bank name.
     * 
     * @param bankName Name of the bank
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Sets the account number.
     * 
     * @param accountNumber Account number
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Sets the verification status of the account.
     * 
     * @param isVerified true if verified, false otherwise
     */
    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
    //getting and setting the balance
    public double getBalance() {
        return balance;
    
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
