package com.ezpay.bank.service;

import com.ezpay.bank.model.Transfer;
import java.util.List;

/**
 * Service interface for handling UPI payment operations in the EZPay banking system.
 * 
 * This interface defines the core functionalities required for initiating and retrieving UPI transfers.
 */
public interface UPIPaymentService {

    /**
     * Processes a new UPI payment transaction.
     * 
     * This method will handle all business logic such as:
     * - Validating the UPI ID format
     * - Checking sufficient balance (mocked or real)
     * - Assigning a unique transfer ID and timestamp
     * - Saving the transaction in the system
     * 
     * @param transfer A {@link Transfer} object containing details like sender ID, receiver UPI ID, and amount.
     * @return A message indicating whether the transaction was successful or failed, with reasons.
     */
    String makeUPIPayment(Transfer transfer);

    /**
     * Retrieves all UPI transfer transactions made by a specific sender.
     * 
     * This can be used for:
     * - Displaying UPI transaction history to a user
     * - Performing audits or analytics on user-specific transfers
     * 
     * @param senderId The ID or account number of the sender whose UPI transactions are to be fetched.
     * @return A list of {@link Transfer} objects associated with the given sender.
     */
    List<Transfer> getTransfersBySender(String senderId);
}
