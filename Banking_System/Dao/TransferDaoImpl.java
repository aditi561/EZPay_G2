package com.ezpay.bank.dao;
import com.ezpay.bank.model.Transfer;

import java.util.*;

public class TransferDaoImpl implements TransferDao {
    private Map<Integer, Transfer> transferMap = new HashMap<>();
    private int transferIdCounter = 1;

    @Override
    public void recordTransfer(Transfer transfer) {
        transfer.setTransferId(transferIdCounter++);
        transferMap.put(transfer.getTransferId(), transfer);
    }

    @Override
    public Transfer getTransferById(int transferId) {
        return transferMap.get(transferId);
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return new ArrayList<>(transferMap.values());
    }
}
