package com.ezpay.bank.controller;

import com.ezpay.bank.model.BankAccount;
import com.ezpay.bank.service.BankingService;
import com.ezpay.bank.service.BankingServiceImpl;

import java.util.List;

/**
 * Controller class for managing bank account operations.
 */
public class BankingServiceController {

    private final BankingService bankingService = new BankingServiceImpl();

    /**
     * Adds a new bank account.
     *
     * @param account The BankAccount object to be added.
     */
    public void addAccount(BankAccount account) {
    	bankingService.addAccount(account);
    }

    /**
     * Retrieves a bank account using the account number.
     *
     * @param accountNumber The account number.
     * @return The BankAccount object if found, else null.
     */
    public BankAccount getAccount(String accountNumber) {
        return bankingService.getAccountByNumber(accountNumber);
    }

    /**
     * Retrieves all bank accounts.
     *
     * @return List of all BankAccount objects.
     */
    public List<BankAccount> getAllAccounts() {
        return bankingService.getAllAccounts();
    }

    /**
     * Updates the details of an existing bank account.
     *
     * @param account The BankAccount object with updated details.
     */
    public void updateAccount(BankAccount account) {
    	bankingService.updateAccount(account);
    }

    /**
     * Deletes a bank account by account number.
     *
     * @param accountNumber The account number of the bank account to delete.
     */
    public void deleteAccount(String accountNumber) {
    	bankingService.deleteAccount(accountNumber);
    }
}
