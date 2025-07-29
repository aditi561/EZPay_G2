
package com.ezpay.bank.dao;

import com.ezpay.bank.model.Transfer;
import java.util.List;

public interface TransferDao {
    void recordTransfer(Transfer transfer);
    Transfer getTransferById(int transferId);
    List<Transfer> getAllTransfers();
}
