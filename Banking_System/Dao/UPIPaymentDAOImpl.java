package com.ezpay.bank.model.dao;

import com.ezpay.bank.model.Transfer;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of the UPIPaymentDAO interface.
 *
 * This class uses an internal list to simulate a database for storing UPI transactions.
 * Useful for testing or prototype purposes before integrating a real database.
 */
public class UPIPaymentDAOImpl implements UPIPaymentDAO {

    // Simulated database: holds all UPI transfer records
    private final List<Transfer> upiTransfers = new ArrayList<>();

    /**
     * Saves a new UPI transfer to the in-memory list.
     * 
     * @param transfer The Transfer object containing UPI payment details
     * @return true if the transfer was added successfully
     */
    @Override
    public boolean saveUPITransfer(Transfer transfer) {
        upiTransfers.add(transfer);  // Add transfer to "DB"
        return true;
    }

    /**
     * Retrieves all UPI transfers made by a specific sender.
     * 
     * This searches through the in-memory list and collects transfers
     * where the sender's account number matches the input.
     *
     * @param senderAccount The sender's account number or user ID
     * @return List of Transfer objects made by the given sender
     */
    @Override
    public List<Transfer> getTransfersBySender(String senderAccount) {
        List<Transfer> result = new ArrayList<>();

        // Filter transfers matching the sender account
        for (Transfer t : upiTransfers) {
            if (t.getSenderAccountNumber().equals(senderAccount)) {
                result.add(t);
            }
        }

        return result;
    }
}
