package com.ezpay.bank.model.dao;

import com.ezpay.bank.model.Transfer;
import java.util.ArrayList;
import java.util.List;

public class UPIPaymentDAOImpl {
	
	private final List<Transfer> upiTransfers = new ArrayList<>();

    @Override
    public boolean saveUPITransfer(Transfer transfer) {
        upiTransfers.add(transfer);
        return true;
    }

    @Override
    public List<Transfer> getTransfersBySender(String senderAccount) {
        List<Transfer> result = new ArrayList<>();
        for (Transfer t : upiTransfers) {
            if (t.getSenderAccountNumber().equals(senderAccount)) {
                result.add(t);
            }
        }
        return result;
    }

}
