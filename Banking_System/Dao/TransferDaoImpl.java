package com.ezpay.bank.dao;

import com.ezpay.bank.model.Transfer;

import java.util.*;

/**
 * TransferDaoImpl provides an in-memory implementation of the TransferDao interface.
 * It manages fund transfers using a HashMap where each transfer is identified by a unique ID.
 */
public class TransferDaoImpl implements TransferDao {
    
    // Stores all transfers mapped by their transfer ID.
    private Map<Integer, Transfer> transferMap = new HashMap<>();
    
    // Auto-increment counter for generating unique transfer IDs.
    private int transferIdCounter = 1;

    /**
     * Records a new fund transfer by assigning it a unique ID and storing it in the map.
     *
     * @param transfer The Transfer object containing the transfer details.
     */
    @Override
    public void recordTransfer(Transfer transfer) {
        transfer.setTransferId(transferIdCounter++);
        transferMap.put(transfer.getTransferId(), transfer);
    }

    /**
     * Retrieves a specific transfer using its unique ID.
     *
     * @param transferId The ID of the transfer to be retrieved.
     * @return The Transfer object if found, otherwise null.
     */
    @Override
    public Transfer getTransferById(int transferId) {
        return transferMap.get(transferId);
    }

    /**
     * Returns a list of all recorded fund transfers.
     *
     * @return A List containing all Transfer objects stored.
     */
    @Override
    public List<Transfer> getAllTransfers() {
        return new ArrayList<>(transferMap.values());
    }
}
