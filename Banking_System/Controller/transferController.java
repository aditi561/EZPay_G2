package com.ezpay.bank.controller;

import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.service.transferService;
import com.ezpay.bank.service.transferServiceImpl;

import java.util.List;

public class transferController {
    private transferService transferService = new transferServiceImpl();

    public void makeTransfer(Transfer transfer) {
        transferService.makeTransfer(transfer);
    }

    public Transfer getTransfer(int id) {
        return transferService.getTransferById(id);
    }

    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }
}
