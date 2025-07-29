package com.ezpay.bank.controller;

import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.service.transferService;
import com.ezpay.bank.service.transferServiceImpl;

import java.util.List;

/**
 * Controller class for managing transfer-related operations.
 */
public class transferController {

    private final transferService transferService = new transferServiceImpl();

    /**
     * Initiates a money transfer.
     *
     * @param transfer The Transfer object containing transfer details.
     */
    public void makeTransfer(Transfer transfer) {
        transferService.makeTransfer(transfer);
    }

    /**
     * Retrieves a specific transfer by ID.
     *
     * @param id The ID of the transfer.
     * @return The Transfer object if found, else null.
     */
    public Transfer getTransfer(int id) {
        return transferService.getTransferById(id);
    }

    /**
     * Retrieves all transfer records.
     *
     * @return List of all Transfer objects.
     */
    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }
}
