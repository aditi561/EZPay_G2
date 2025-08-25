package com.nwg.ezpay.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.entity.UPITransaction;
import com.nwg.ezpay.exception.AccountNotFoundException;
import com.nwg.ezpay.exception.InsufficientBalanceException;
import com.nwg.ezpay.exception.InvalidAmountException;
import com.nwg.ezpay.exception.InvalidPinException;
import com.nwg.ezpay.exception.TransactionNotFoundException;
import com.nwg.ezpay.repository.UPIAccountRepository;
import com.nwg.ezpay.repository.UPITransactionRepository;

/**
 * Service class for managing UPI transactions.
 * Handles business logic for creating, processing, and managing UPI transactions.
 */
@Service
public class UPITransactionService {

    private final UPITransactionRepository upiTransactionRepository;
    private final UPIAccountRepository upiAccountRepository;

    @Autowired
    public UPITransactionService(UPITransactionRepository upiTransactionRepository,
                                 UPIAccountRepository upiAccountRepository) {
        this.upiTransactionRepository = upiTransactionRepository;
        this.upiAccountRepository = upiAccountRepository;
    }

    /**
     * Creates a new UPI transaction after validating the details.
     */
    public UPITransaction addUPITransaction(UPITransaction upiTransaction) {
        if (upiTransaction.getReceiverUpiId() == null ||
                !upiTransaction.getReceiverUpiId().matches("^[a-zA-Z0-9._-]+@[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid UPI ID format.");
        }

        if (upiTransaction.getAmount() == null || upiTransaction.getAmount() <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }

        UPIAccount sender = upiAccountRepository.findByUpiId(upiTransaction.getSenderUpiId())
                .orElseThrow(() -> new AccountNotFoundException(
                        "Sender account not found for UPI ID: " + upiTransaction.getSenderUpiId()
                ));

        if (upiTransaction.getAmount().compareTo(sender.getBalance()) > 0) {
            throw new InsufficientBalanceException("Insufficient balance in sender's account.");
        }

        upiTransaction.setStatus("PENDING");
        upiTransaction.setTimestamp(LocalDateTime.now());

        return upiTransactionRepository.save(upiTransaction);
    }

    /**
     * Verifies a transaction using PIN and processes it.
     */
    public UPITransaction verifyTransactionPin(Integer transactionId, String pin) {
        UPITransaction transaction = upiTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (!"PENDING".equals(transaction.getStatus())) {
            throw new IllegalStateException("Transaction already processed.");
        }

        UPIAccount sender = upiAccountRepository.findByUpiId(transaction.getSenderUpiId())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        if (!sender.getPin().equals(pin)) {
            throw new InvalidPinException("Invalid PIN provided");
        }

        if (sender.getBalance() < transaction.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance in sender's account");
        }

        // Deduct money
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        upiAccountRepository.save(sender);

        // Mark transaction as successful
        transaction.setStatus("SUCCESS");
        return upiTransactionRepository.save(transaction);
    }

    /**
     * Retrieves all UPI transactions.
     */
    public List<UPITransaction> showAllUPITransactions() {
        return upiTransactionRepository.findAll();
    }

    /**
     * Deletes a UPI transaction by ID.
     */
    public boolean deleteUPITransaction(int id) {
        if (upiTransactionRepository.existsById(id)) {
            upiTransactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a specific transaction by ID.
     */
    public Optional<UPITransaction> getUPITransactionById(int id) {
        return upiTransactionRepository.findById(id);
    }

    /**
     * Retrieves all transactions with a specific status.
     */
    public List<UPITransaction> getUPITransactionsByStatus(String status) {
        return upiTransactionRepository.findByStatus(status);
    }
}
