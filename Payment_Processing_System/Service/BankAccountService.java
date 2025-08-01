package com.ezpay.bank.service;

import com.ezpay.bank.model.BankAccount;
import java.util.List;

/**
 * Interface for managing bank account-related operations.
 */
public interface BankAccountService {

    /**
     * Adds a new bank account.
     *
     * @param account The BankAccount object to be added.
     */
    void addAccount(BankAccount account);

    /**
     * Retrieves a bank account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return The BankAccount object if found, otherwise null.
     */
    BankAccount getAccountByNumber(String accountNumber);

    /**
     * Retrieves all bank accounts.
     *
     * @return A list of all BankAccount objects.
     */
    List<BankAccount> getAllAccounts();

    /**
     * Updates the details of an existing bank account.
     *
     * @param account The BankAccount object with updated details.
     */
    void updateAccount(BankAccount account);

    /**
     * Deletes a bank account based on its account number.
     *
     * @param accountNumber The account number of the account to be deleted.
     */
    void deleteAccount(String accountNumber);
}
