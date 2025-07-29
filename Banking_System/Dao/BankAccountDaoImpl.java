package com.ezpay.bank.dao;
import com.ezpay.bank.model.BankAccount;

import java.util.*;

public class BankAccountDaoImpl implements BankAccountDao {
    private Map<String, BankAccount> accountMap = new HashMap<>();

    @Override
    public void addAccount(BankAccount account) {
        accountMap.put(account.getAccountNumber(), account);
    }

    @Override
    public BankAccount getAccountByNumber(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accountMap.values());
    }

    @Override
    public void updateAccount(BankAccount account) {
        accountMap.put(account.getAccountNumber(), account);
    }

    @Override
    public void deleteAccount(String accountNumber) {
        accountMap.remove(accountNumber);
    }
}
