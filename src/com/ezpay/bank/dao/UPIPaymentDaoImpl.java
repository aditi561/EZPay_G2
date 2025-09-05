package com.ezpay.bank.dao;

import com.ezpay.bank.model.Transfer;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of the UPIPaymentDAO interface.
 *
 * This class uses an internal list to simulate a database for storing UPI transactions.
 * Useful for testing or prototype purposes before integrating a real database.
 */
public class UPIPaymentDaoImpl implements UPIPaymentDao {

    // Simulated database: holds all UPI transfer records
    private final List<Transfer> upiTransfers = new ArrayList<>();

    /**
     * Saves a new UPI transfer to the in-memory list with validations.
     *
     * @param transfer The Transfer object containing UPI payment details
     * @return true if the transfer was added successfully, false otherwise
     */
    @Override
    public boolean saveUPITransfer(Transfer transfer) {
        // Reject null transfer
        if (transfer == null) {
            return false;
        }

        // Reject null or empty sender
        if (transfer.getSenderAccountNumber() == null || transfer.getSenderAccountNumber().isBlank()) {
            return false;
        }

        // Reject null or empty receiver
        if (transfer.getReceiverAccountNumber() == null || transfer.getReceiverAccountNumber().isBlank()) {
            return false;
        }

        // Reject negative or zero amount
        if (transfer.getAmount() <= 0) {
            return false;
        }

        // Passed all validations → save transfer
        upiTransfers.add(transfer);
        return true;
    }

    /**
     * Retrieves all UPI transfers made by a specific sender.
     *
     * @param senderAccount The sender's account number or user ID
     * @return List of Transfer objects made by the given sender (empty if none found)
     */
    @Override
    public List<Transfer> getTransfersBySender(String senderAccount) {
        List<Transfer> result = new ArrayList<>();

        // Handle null or empty sender gracefully
        if (senderAccount == null || senderAccount.isBlank()) {
            return result;
        }

        // Filter transfers matching the sender account
        for (Transfer t : upiTransfers) {
            if (senderAccount.equals(t.getSenderAccountNumber())) {
                result.add(t);
            }
        }

        return result;
    }
}
