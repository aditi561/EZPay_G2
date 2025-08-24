package com.ezpay.bank.dao;

import com.ezpay.bank.model.Transfer;
import java.util.List;

/**
 * Data Access Object (DAO) interface for UPI payment operations.
 * 
 * This interface defines methods for persisting and retrieving UPI transfer data.
 * Implementations may store data in-memory, in a database, or through external APIs.
 */
public interface UPIPaymentDao {

    /**
     * Saves a UPI transfer record.
     * 
     * This method is responsible for persisting the details of a completed UPI transaction.
     * It may include assigning unique IDs, timestamps, and setting transaction status before saving.
     *
     * @param transfer A {@link Transfer} object containing details of the UPI payment
     * @return true if saved successfully, false otherwise
     */
    boolean saveUPITransfer(Transfer transfer);

    /**
     * Retrieves all UPI transfers initiated by a specific sender.
     *
     * This method can be used to fetch the UPI transaction history of a particular user/account.
     *
     * @param senderAccount The sender's account number or user ID
     * @return A list of {@link Transfer} objects initiated by the sender
     */
    List<Transfer> getTransfersBySender(String senderAccount);
}
