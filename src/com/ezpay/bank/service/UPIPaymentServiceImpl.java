package com.ezpay.bank.service;

import com.ezpay.bank.dao.UPIPaymentDao;
import com.ezpay.bank.dao.UPIPaymentDaoImpl;
import com.ezpay.bank.model.Transfer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the UPIPaymentService interface.
 * 
 * This class contains business logic for validating and processing UPI payments,
 * as well as retrieving transaction history for a given sender.
 */
public class UPIPaymentServiceImpl implements UPIPaymentService {

    // DAO for interacting with the underlying data store (in-memory or persistent)
    private final UPIPaymentDao dao = new UPIPaymentDaoImpl();

    /**
     * Executes the UPI payment transaction.
     * 
     * This method performs the following:
     * - Validates the UPI ID format.
     * - Checks if the sender has sufficient funds (mock logic).
     * - Generates transaction ID and timestamp.
     * - Marks the transaction as successful or failed.
     * - Persists the transfer record using DAO.
     * 
     * @param transfer Transfer object containing sender ID, receiver UPI ID, amount, etc.
     * @return A result message indicating success or failure.
     */
    @Override
    public String makeUPIPayment(Transfer transfer) {
        // Validate receiver's UPI ID
        if (!isValidUPI(transfer.getReceiverAccountNumber())) {
            transfer.setStatus(false);
            return " Invalid UPI ID.";
        }

        // Check sender's balance (mocked logic)
        if (!hasSufficientFunds(transfer.getSenderAccountNumber(), transfer.getAmount())) {
            transfer.setStatus(false);
            return "Insufficient balance.";
        }

        // Add timestamp and generate transaction ID
        transfer.setTransferDateTime(LocalDateTime.now());
        transfer.setTransferId(generateTransactionId());
        transfer.setStatus(true);

        // Persist the transaction
        dao.saveUPITransfer(transfer);

        return "UPI Payment Successful! Transaction ID: " + transfer.getTransferId();
    }

    /**
     * Retrieves all UPI transactions made by a specific sender.
     * 
     * @param senderId ID or account number of the sender.
     * @return A list of Transfer objects initiated by the sender.
     */
    @Override
    public List<Transfer> getTransfersBySender(String senderId) {
        return dao.getTransfersBySender(senderId);
    }

    /**
     * Validates the format of a UPI ID using regex.
     * A valid UPI ID should follow the pattern: user@provider
     * 
     * @param upiId Receiver's UPI ID
     * @return true if valid, false otherwise
     */
    private boolean isValidUPI(String upiId) {
        return upiId != null && upiId.matches("^[\\w.-]+@[\\w.-]+$");
    }

    /**
     * Checks if the sender has sufficient funds to make the transaction.
     * This is mock logic assuming a ₹50,000 balance limit.
     * 
     * @param senderAccount Sender's account number or user ID
     * @param amount Amount to be transferred
     * @return true if funds are sufficient, false otherwise
     */
    private boolean hasSufficientFunds(String senderAccount, double amount) {
        return amount <= 50000;
    }

    /**
     * Generates a 6-digit random transaction ID.
     * 
     * @return A unique transaction ID
     */
    private int generateTransactionId() {
        return new Random().nextInt(900000) + 100000;
    }
}
