package com.ezpay.bank.controller;

import com.ezpay.bank.model.BankAccount;
import com.ezpay.bank.service.BankAccountService;
import com.ezpay.bank.service.BankAccountServiceImpl;

import java.util.List;

/**
 * Controller class for managing bank account operations.
 */
public class BankAccountController {

    private final BankAccountService bankAccountService = new BankAccountServiceImpl();

    /**
     * Adds a new bank account.
     *
     * @param account The BankAccount object to be added.
     */
    public void addAccount(BankAccount account) {
        bankAccountService.addAccount(account);
    }

    /**
     * Retrieves a bank account using the account number.
     *
     * @param accountNumber The account number.
     * @return The BankAccount object if found, else null.
     */
    public BankAccount getAccount(String accountNumber) {
        return bankAccountService.getAccountByNumber(accountNumber);
    }

    /**
     * Retrieves all bank accounts.
     *
     * @return List of all BankAccount objects.
     */
    public List<BankAccount> getAllAccounts() {
        return bankAccountService.getAllAccounts();
    }

    /**
     * Updates the details of an existing bank account.
     *
     * @param account The BankAccount object with updated details.
     */
    public void updateAccount(BankAccount account) {
        bankAccountService.updateAccount(account);
    }

    /**
     * Deletes a bank account by account number.
     *
     * @param accountNumber The account number of the bank account to delete.
     */
    public void deleteAccount(String accountNumber) {
        bankAccountService.deleteAccount(accountNumber);
    }
}
