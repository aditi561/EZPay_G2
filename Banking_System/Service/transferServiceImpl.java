package com.ezpay.bank.service;

import com.ezpay.bank.dao.TransferDao;
import com.ezpay.bank.dao.TransferDaoImpl;
import com.ezpay.bank.model.Transfer;

import java.util.List;

public class transferServiceImpl implements transferService {

    private TransferDao transferDao = new TransferDaoImpl();

    @Override
    public void makeTransfer(Transfer transfer) {
        transferDao.recordTransfer(transfer);
    }

    @Override
    public Transfer getTransferById(int transferId) {
        return transferDao.getTransferById(transferId);
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }
}
