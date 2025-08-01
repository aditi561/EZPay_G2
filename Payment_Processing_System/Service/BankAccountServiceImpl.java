package com.ezpay.bank.service;

import com.ezpay.bank.dao.BankAccountDao;
import com.ezpay.bank.dao.BankAccountDaoImpl;
import com.ezpay.bank.model.BankAccount;

import java.util.List;

/**
 * Implementation of BankAccountService for handling bank account operations.
 */
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountDao bankAccountDao = new BankAccountDaoImpl();

    /**
     * Adds a new bank account.
     *
     * @param account The BankAccount object to be added.
     */
    @Override
    public void addAccount(BankAccount account) {
        bankAccountDao.addAccount(account);
    }

    /**
     * Retrieves a bank account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return The BankAccount object if found, otherwise null.
     */
    @Override
    public BankAccount getAccountByNumber(String accountNumber) {
        return bankAccountDao.getAccountByNumber(accountNumber);
    }

    /**
     * Retrieves all bank accounts.
     *
     * @return A list of all BankAccount objects.
     */
    @Override
    public List<BankAccount> getAllAccounts() {
        return bankAccountDao.getAllAccounts();
    }

    /**
     * Updates an existing bank account's details.
     *
     * @param account The BankAccount object with updated information.
     */
    @Override
    public void updateAccount(BankAccount account) {
        bankAccountDao.updateAccount(account);
    }

    /**
     * Deletes a bank account using its account number.
     *
     * @param accountNumber The account number of the bank account to be deleted.
     */
    @Override
    public void deleteAccount(String accountNumber) {
        bankAccountDao.deleteAccount(accountNumber);
    }
}
