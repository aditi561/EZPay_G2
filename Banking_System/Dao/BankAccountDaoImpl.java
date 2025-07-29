package com.ezpay.bank.dao;

import com.ezpay.bank.model.BankAccount;
import java.util.*;

/**
 * Implementation of the BankAccountDao interface using an in-memory HashMap.
 * This class provides CRUD operations for managing BankAccount entities.
 */
public class BankAccountDaoImpl implements BankAccountDao {

    // Stores bank accounts with account number as the key.
    private Map<String, BankAccount> accountMap = new HashMap<>();

    /**
     * Adds a new bank account to the system.
     * If an account with the same account number already exists, it will be overwritten.
     *
     * @param account The BankAccount object to be added.
     */
    @Override
    public void addAccount(BankAccount account) {
        accountMap.put(account.getAccountNumber(), account);
    }

    /**
     * Retrieves a bank account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return The corresponding BankAccount object, or null if not found.
     */
    @Override
    public BankAccount getAccountByNumber(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    /**
     * Returns a list of all bank accounts stored in memory.
     *
     * @return A list of BankAccount objects.
     */
    @Override
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accountMap.values());
    }

    /**
     * Updates an existing bank account.
     * If the account doesn't exist, it will be added.
     *
     * @param account The BankAccount object with updated information.
     */
    @Override
    public void updateAccount(BankAccount account) {
        accountMap.put(account.getAccountNumber(), account);
    }

    /**
     * Deletes a bank account from the system using the account number.
     *
     * @param accountNumber The account number of the bank account to delete.
     */
    @Override
    public void deleteAccount(String accountNumber) {
        accountMap.remove(accountNumber);
    }
}
