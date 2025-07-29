package com.ezpay.bank.model.dao;

import com.ezpay.bank.model.Transfer;
import java.util.List;

public interface UPIPaymentDAO {
    boolean saveUPITransfer(Transfer transfer);
    List<Transfer> getTransfersBySender(String senderAccount);
}
