package com.ezpay.bank.model.dao;

import com.ezpay.bank.model.Transfer;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Muskan Sinha
 * Reviewer: [Reviewer Name]
 * Date of Review: [DD-MM-YYYY]
 *
 * This class represents the in-memory implementation of the UPIPaymentDAO interface.
 * It simulates a database using a list of Transfer objects for UPI transactions.
 */

// Class Naming Convention: Follows PascalCase as per Java standards
public class UPIPaymentDAOImpl implements UPIPaymentDAO {

    // All Variables Named Properly, Variable Naming: Descriptive and camelCase
    private final List<Transfer> upiTransfers = new ArrayList<>(); // Class Members

    /**
     * Business Logic Comments:
     * Adds a new UPI transfer to the in-memory list (simulated DB).
     *
     * @param transfer Transfer object representing payment details  // Input/Output Parameters Mentioned
     * @return true if transfer is saved successfully
     */
    @Override
    public boolean saveUPITransfer(Transfer transfer) {
        // Comments Before Validation Logic: No validation needed here for demo
        upiTransfers.add(transfer);
        return true;
    }

    /**
     * Business Logic Comments:
     * Fetches all UPI transactions by sender's account number.
     *
     * @param senderAccount Sender's UPI/account identifier  // Input/Output Parameters Mentioned
     * @return List of transfers by that sender
     */
    @Override
    public List<Transfer> getTransfersBySender(String senderAccount) {
        List<Transfer> result = new ArrayList<>();

        for (Transfer t : upiTransfers) {
            if (t.getSenderAccountNumber().equals(senderAccount)) {
                result.add(t);
            }
        }

        return result;
    }
}
