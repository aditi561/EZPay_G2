
package com.ezpay.bank.dao;

import com.ezpay.bank.model.BankAccount;
import java.util.List;

public interface BankAccountDao {
    void addAccount(BankAccount account);
    BankAccount getAccountByNumber(String accountNumber);
    List<BankAccount> getAllAccounts();
    void updateAccount(BankAccount account);
    void deleteAccount(String accountNumber);
}
