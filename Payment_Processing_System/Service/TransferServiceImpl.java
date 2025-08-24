package com.ezpay.bank.service;

import com.ezpay.bank.dao.TransferDao;
import com.ezpay.bank.dao.TransferDaoImpl;
import com.ezpay.bank.model.BankAccount;
import com.ezpay.bank.model.Transfer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Implementation of the TransferService interface for managing transfers.
 */
public class TransferServiceImpl implements TransferService {

    private final TransferDao transferDao = new TransferDaoImpl();

    // Simulated in-memory account store
    private final Map<String, BankAccount> accountStore = new HashMap<>();

    public void addAccount(BankAccount account) {
        accountStore.put(account.getAccountNumber(), account);
    }

    public BankAccount getAccount(String accountNumber) {
        return accountStore.get(accountNumber);
    }

    @Override
    public void makeTransfer(Transfer transfer) {
        BankAccount sender = accountStore.get(transfer.getSenderAccountNumber());
        BankAccount receiver = accountStore.get(transfer.getReceiverAccountNumber());

        if (sender == null || receiver == null) {
            transfer.setStatus(false);
        } else if (transfer.getAmount() <= 0 || sender.getBalance() < transfer.getAmount()) {
            transfer.setStatus(false);
        } else {
            sender.setBalance(sender.getBalance() - transfer.getAmount());
            receiver.setBalance(receiver.getBalance() + transfer.getAmount());
            transfer.setStatus(true);
        }

        transfer.setTransferDateTime(LocalDateTime.now());
        transferDao.recordTransfer(transfer);
    }

    @Override
    public Transfer getTransferById(int transferId) {
        return transferDao.getTransferById(transferId);
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }
}
