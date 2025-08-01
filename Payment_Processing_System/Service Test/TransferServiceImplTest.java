package com.ezpay.bank.service_test;

import static org.junit.jupiter.api.Assertions.*;

import com.ezpay.bank.model.BankAccount;
import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.controller.TransferController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/** This test class tests for multiple test service for Transfer 
    Service for users an service.
 * Unit tests for Transfer operations

 @author - Aziz
 @version - 1.0
 */
public class TransferServiceImplTest {

    private TransferController transferController;

    private BankAccount senderAccount;
    private BankAccount receiverAccount;

    @BeforeEach
    public void setUp() {
        transferController = new TransferController();

        senderAccount = new BankAccount(1, "ICICI", "ACC123", true);
        receiverAccount = new BankAccount(2, "HDFC", "ACC456", true);

        senderAccount.setBalance(1000.0);
        receiverAccount.setBalance(1000.0);

        transferController.addAccount(senderAccount);
        transferController.addAccount(receiverAccount);
    }

    @Test
    public void testSuccessfulTransfer() {
        Transfer transfer = new Transfer(0, "ACC123", "ACC456", 200.0, LocalDateTime.now(), true);
        transferController.makeTransfer(transfer);

        BankAccount updatedSender = transferController.getAccount("ACC123");
        BankAccount updatedReceiver = transferController.getAccount("ACC456");

        assertEquals(800.0, updatedSender.getBalance(), 0.01);
        assertEquals(1200.0, updatedReceiver.getBalance(), 0.01);
        assertTrue(transfer.isStatus());
    }

    @Test
    public void testInsufficientFundsTransfer() {
        Transfer transfer = new Transfer(0, "ACC123", "ACC456", 2000.0, LocalDateTime.now(), true);
        transferController.makeTransfer(transfer);

        BankAccount updatedSender = transferController.getAccount("ACC123");
        BankAccount updatedReceiver = transferController.getAccount("ACC456");

        assertEquals(1000.0, updatedSender.getBalance(), 0.01);
        assertEquals(1000.0, updatedReceiver.getBalance(), 0.01);
        assertFalse(transfer.isStatus());
    }

    @Test
    public void testZeroAmountTransfer() {
        Transfer transfer = new Transfer(0, "ACC123", "ACC456", 0.0, LocalDateTime.now(), true);
        transferController.makeTransfer(transfer);

        BankAccount updatedSender = transferController.getAccount("ACC123");
        BankAccount updatedReceiver = transferController.getAccount("ACC456");

        assertEquals(1000.0, updatedSender.getBalance(), 0.01);
        assertEquals(1000.0, updatedReceiver.getBalance(), 0.01);
        assertFalse(transfer.isStatus());
    }
}
