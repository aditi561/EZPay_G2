package com.ezpay.bank.controller;

import com.ezpay.bank.model.BankAccount;
import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.service.TransferService;
import com.ezpay.bank.service.TransferServiceImpl;

import java.util.List;

/**
 * Controller class for managing transfer-related operations.
 */
public class TransferController {

    private final TransferServiceImpl transferService = new TransferServiceImpl();

    public void addAccount(BankAccount account) {
        transferService.addAccount(account);
    }

    public BankAccount getAccount(String accountNumber) {
        return transferService.getAccount(accountNumber);
    }

    public void makeTransfer(Transfer transfer) {
        transferService.makeTransfer(transfer);
    }

    public Transfer getTransfer(int id) {
        return transferService.getTransferById(id);
    }

    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }
}
