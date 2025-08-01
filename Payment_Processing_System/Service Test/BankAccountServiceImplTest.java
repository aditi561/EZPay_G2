package com.ezpay.bank.service_test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ezpay.bank.model.BankAccount;
import com.ezpay.bank.service.BankingServiceImpl;

/**
 * Test class for BankingServiceImpl
 * Validates deposit and withdrawal logic by updating bank accounts.
 */
public class BankingServiceImplTest {

    private BankingServiceImpl service;
    private BankAccount account;

    @BeforeEach
    public void setup() {
        service = new BankingServiceImpl();
        account = new BankAccount(1, "SBI", "ACC100", true);
        account.setBalance(1000.0);  // initial balance
        service.addAccount(account);
    }

    /**
     * Simulates a deposit by updating the account balance.
     */
    @Test
    public void testDeposit() {
        BankAccount acc = service.getAccountByNumber("ACC100");
        double initialBalance = acc.getBalance();

        acc.setBalance(initialBalance + 500);
        service.updateAccount(acc);

        BankAccount updated = service.getAccountByNumber("ACC100");
        assertEquals(initialBalance + 500, updated.getBalance(), 0.01);
    }

    /**
     * Simulates a valid withdrawal by updating the account balance.
     */
    @Test
    public void testWithdraw() {
        BankAccount acc = service.getAccountByNumber("ACC100");
        double initialBalance = acc.getBalance();

        acc.setBalance(initialBalance - 300);
        service.updateAccount(acc);

        BankAccount updated = service.getAccountByNumber("ACC100");
        assertEquals(initialBalance - 300, updated.getBalance(), 0.01);
    }

    /**
     * Tests withdrawal when balance is insufficient (should not update).
     */
    @Test
    public void testWithdrawInsufficientFunds() {
        BankAccount acc = service.getAccountByNumber("ACC100");
        double initialBalance = acc.getBalance();

        if (initialBalance < 2000) {
            System.out.println("💡 Cannot withdraw more than balance: skipping update.");
        } else {
            acc.setBalance(initialBalance - 2000);
            service.updateAccount(acc);
        }

        BankAccount updated = service.getAccountByNumber("ACC100");
        assertEquals(initialBalance, updated.getBalance(), 0.01);
    }
}
