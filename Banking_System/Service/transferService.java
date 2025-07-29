package com.ezpay.bank.service;

import com.ezpay.bank.model.Transfer;
import java.util.List;

public interface transferService {
    public void makeTransfer(Transfer transfer);
    public Transfer getTransferById(int transferId);
    public List<Transfer> getAllTransfers();
}
