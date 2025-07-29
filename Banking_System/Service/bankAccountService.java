package com.ezpay.bank.service;

import com.ezpay.bank.model.BankAccount;
import java.util.List;

public interface bankAccountService {
    public void addAccount(BankAccount account);
    public BankAccount getAccountByNumber(String accountNumber);
    public List<BankAccount> getAllAccounts();
    public void updateAccount(BankAccount account);
    public void deleteAccount(String accountNumber);
}
