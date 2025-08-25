package com.nwg.ezpay.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.entity.UPITransaction;
import com.nwg.ezpay.exception.InsufficientBalanceException;
import com.nwg.ezpay.exception.InvalidAmountException;
import com.nwg.ezpay.exception.InvalidPinException;
import com.nwg.ezpay.exception.TransactionNotFoundException;
import com.nwg.ezpay.repository.UPIAccountRepository;
import com.nwg.ezpay.repository.UPITransactionRepository;

import com.nwg.ezpay.exception.*;

/**
 * Service class for managing UPI transactions.
 * Handles business logic for creating, processing, and managing UPI
 * transactions.
 */
@Service
public class UPITransactionService {

    /** Repository for UPI transaction data access operations */
    private final UPITransactionRepository upiTransactionRepository;

    /** Repository for UPI account data access operations */
    private final UPIAccountRepository upiAccountRepository;

    /**
     * Constructor for dependency injection
     * 
     * @param upiTransactionRepository repository for transaction operations
     * @param upiAccountRepository     repository for account operations
     */
    @Autowired
    public UPITransactionService(UPITransactionRepository upiTransactionRepository,
            UPIAccountRepository upiAccountRepository) {
        this.upiTransactionRepository = upiTransactionRepository;
        this.upiAccountRepository = upiAccountRepository;
    }

    /**
     * Creates a new UPI transaction after validating the details
     * 
     * @param upiTransaction the transaction details to create
     * @return the created transaction
     * @throws IllegalArgumentException if UPI ID format is invalid, amount is
     *                                  invalid,
     *                                  sender account not found, or insufficient
     *                                  balance
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
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found for UPI ID: " 
                        + upiTransaction.getSenderUpiId()));

        if (upiTransaction.getAmount().compareTo(sender.getBalance()) > 0) {
            throw new InsufficientBalanceException("Insufficient balance in sender's account.");
        }

        upiTransaction.setStatus("PENDING");
        upiTransaction.setTimestamp(LocalDateTime.now());

        return upiTransactionRepository.save(upiTransaction);
    }

    /**
     * Verifies a transaction using PIN and processes it
     * 
     * @param transactionId ID of the transaction to verify
     * @param pin           PIN to verify against sender's account
     * @return the processed transaction with updated status
     * @throws NoSuchElementException if transaction or sender account not found
     * @throws IllegalStateException  if transaction is already processed
     */
    public UPITransaction verifyTransactionPin(Integer transactionId, String pin) {
        UPITransaction transaction = upiTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + transactionId));

        if (!"PENDING".equals(transaction.getStatus())) {
            throw new IllegalStateException("Transaction already processed.");
        }

        UPIAccount sender = upiAccountRepository.findByUpiId(transaction.getSenderUpiId())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found."));

        if (!sender.getPin().equals(pin)) {
            throw new InvalidPinException("Invalid PIN provided.");
        }

        if (sender.getBalance() < transaction.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance in sender's account.");
        }

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        upiAccountRepository.save(sender);

        transaction.setStatus("SUCCESS");
        return upiTransactionRepository.save(transaction);
    }
    // public UPITransaction verifyTransactionPin(Integer transactionId, String pin) {
    //     UPITransaction transaction = upiTransactionRepository.findById(transactionId)
    //             .orElseThrow(() -> new NoSuchElementException("Transaction not found."));

    //     if (!"PENDING".equals(transaction.getStatus())) {
    //         throw new IllegalStateException("Transaction already processed.");
    //     }

    //     UPIAccount sender = upiAccountRepository.findByUpiId(transaction.getSenderUpiId())
    //             .orElseThrow(() -> new NoSuchElementException("Sender account not found."));

    //     // ✅ Compare with hashed PIN if you store it hashed
    //     if (!sender.getPin().equals(pin)) {
    //         transaction.setStatus("FAILED");
    //         return upiTransactionRepository.save(transaction);
    //     }

    //     // ✅ Balance check
    //     if (sender.getBalance() < transaction.getAmount()) {
    //         transaction.setStatus("FAILED");
    //         return upiTransactionRepository.save(transaction);
    //     }

    //     // Deduct money
    //     sender.setBalance(sender.getBalance() - transaction.getAmount());
    //     upiAccountRepository.save(sender);

    //     // Update transaction
    //     transaction.setStatus("SUCCESS");
    //     return upiTransactionRepository.save(transaction);
    // }

    /**
     * Retrieves all UPI transactions
     * 
     * @return list of all UPI transactions
     */
    public List<UPITransaction> showAllUPITransactions() {
        return upiTransactionRepository.findAll();
    }

    /**
     * Deletes a UPI transaction by ID
     * 
     * @param id ID of the transaction to delete
     * @return true if transaction was deleted, false if not found
     */
    public boolean deleteUPITransaction(int id) {
        if (upiTransactionRepository.existsById(id)) {
            upiTransactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a specific transaction by ID
     * 
     * @param id ID of the transaction to find
     * @return Optional containing the transaction if found, empty otherwise
     */
    public Optional<UPITransaction> getUPITransactionById(int id) {
        return upiTransactionRepository.findById(id);
    }

    /**
     * Retrieves all transactions with a specific status
     * 
     * @param status the status to filter by (e.g., "PENDING", "SUCCESS", "FAILED")
     * @return list of transactions matching the given status
     */
    public List<UPITransaction> getUPITransactionsByStatus(String status) {
        return upiTransactionRepository.findByStatus(status);
    }
}
