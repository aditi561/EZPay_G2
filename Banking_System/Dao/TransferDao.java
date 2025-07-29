package com.ezpay.bank.dao;

import com.ezpay.bank.model.Transfer;
import java.util.List;

/**
 * TransferDao defines the data access operations for managing fund transfer records
 * within the EzPay banking system.
 */
public interface TransferDao {

    /**
     * Records a new fund transfer in the system.
     *
     * @param transfer The Transfer object containing the transfer details.
     */
    void recordTransfer(Transfer transfer);

    /**
     * Retrieves a transfer by its unique transfer ID.
     *
     * @param transferId The ID of the transfer to retrieve.
     * @return The Transfer object if found, or null if not found.
     */
    Transfer getTransferById(int transferId);

    /**
     * Retrieves a list of all fund transfers recorded in the system.
     *
     * @return A list of Transfer objects.
     */
    List<Transfer> getAllTransfers();
}
