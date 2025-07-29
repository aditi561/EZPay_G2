package com.ezpay.bank.dao;

import com.ezpay.bank.model.BankAccount;
import java.util.List;

/**
 * Interface for performing CRUD operations on BankAccount entities.
 * This provides an abstraction for managing bank accounts in the system.
 */
public interface BankAccountDao {

    /**
     * Adds a new bank account to the system.
     *
     * @param account The BankAccount object to be added.
     */
    void addAccount(BankAccount account);

    /**
     * Retrieves a bank account using its account number.
     *
     * @param accountNumber The account number to search for.
     * @return The BankAccount object if found; otherwise, null.
     */
    BankAccount getAccountByNumber(String accountNumber);

    /**
     * Retrieves all bank accounts stored in the system.
     *
     * @return A list of all BankAccount objects.
     */
    List<BankAccount> getAllAccounts();

    /**
     * Updates the details of an existing bank account.
     * If the account does not exist, this may add it as a new one based on implementation.
     *
     * @param account The updated BankAccount object.
     */
    void updateAccount(BankAccount account);

    /**
     * Deletes a bank account from the system using its account number.
     *
     * @param accountNumber The account number of the account to delete.
     */
    void deleteAccount(String accountNumber);
}
