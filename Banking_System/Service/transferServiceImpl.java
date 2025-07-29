package com.ezpay.bank.service;

import com.ezpay.bank.dao.TransferDao;
import com.ezpay.bank.dao.TransferDaoImpl;
import com.ezpay.bank.model.Transfer;

import java.util.List;

/**
 * Implementation of the TransferService interface for managing transfers.
 */
public class transferServiceImpl implements transferService {

    private final TransferDao transferDao = new TransferDaoImpl();

    /**
     * Records a new transfer by delegating to the DAO.
     *
     * @param transfer The Transfer object to be recorded.
     */
    @Override
    public void makeTransfer(Transfer transfer) {
        transferDao.recordTransfer(transfer);
    }

    /**
     * Retrieves a transfer by its ID.
     *
     * @param transferId The ID of the transfer.
     * @return The Transfer object, or null if not found.
     */
    @Override
    public Transfer getTransferById(int transferId) {
        return transferDao.getTransferById(transferId);
    }

    /**
     * Retrieves a list of all transfers.
     *
     * @return A list of Transfer objects.
     */
    @Override
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }
}
