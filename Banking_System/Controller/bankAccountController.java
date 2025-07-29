package com.ezpay.bank.controller;

import com.ezpay.bank.model.BankAccount;
import com.ezpay.bank.service.bankAccountService;
import com.ezpay.bank.service.bankAccountServiceImpl;

import java.util.List;

public class bankAccountController {
    private bankAccountService bankAccountService = new bankAccountServiceImpl();

    public void addAccount(BankAccount account) {
        bankAccountService.addAccount(account);
    }

    public BankAccount getAccount(String accountNumber) {
        return bankAccountService.getAccountByNumber(accountNumber);
    }

    public List<BankAccount> getAllAccounts() {
        return bankAccountService.getAllAccounts();
    }

    public void updateAccount(BankAccount account) {
        bankAccountService.updateAccount(account);
    }

    public void deleteAccount(String accountNumber) {
        bankAccountService.deleteAccount(accountNumber);
    }
}
