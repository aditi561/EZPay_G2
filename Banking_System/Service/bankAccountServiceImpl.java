package com.ezpay.bank.service;

import com.ezpay.bank.dao.BankAccountDao;
import com.ezpay.bank.dao.BankAccountDaoImpl;
import com.ezpay.bank.model.BankAccount;

import java.util.List;

public class bankAccountServiceImpl implements bankAccountService {

    private BankAccountDao bankAccountDao = new BankAccountDaoImpl();

    @Override
    public void addAccount(BankAccount account) {
        bankAccountDao.addAccount(account);
    }

    @Override
    public BankAccount getAccountByNumber(String accountNumber) {
        return bankAccountDao.getAccountByNumber(accountNumber);
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return bankAccountDao.getAllAccounts();
    }

    @Override
    public void updateAccount(BankAccount account) {
        bankAccountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(String accountNumber) {
        bankAccountDao.deleteAccount(accountNumber);
    }
}
