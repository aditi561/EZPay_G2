package com.ezpay.bank.service;

import com.ezpay.bank.model.Transfer;
import java.util.List;

/**
 * Service interface for handling transfer-related operations.
 */
public interface TransferService {

    /**
     * Records a new transfer.
     *
     * @param transfer The Transfer object to be recorded.
     */
    void makeTransfer(Transfer transfer);

    /**
     * Retrieves a transfer by its unique ID.
     *
     * @param transferId The ID of the transfer to retrieve.
     * @return The Transfer object if found, otherwise null.
     */
    Transfer getTransferById(int transferId);

    /**
     * Retrieves all recorded transfers.
     *
     * @return A list of all Transfer objects.
     */
    List<Transfer> getAllTransfers();
}
