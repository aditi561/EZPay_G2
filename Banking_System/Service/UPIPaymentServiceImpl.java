package com.ezpay.bank.service;

import com.ezpay.bank.dao.UPIPaymentDAO;
import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.model.dao.UPIPaymentDAOImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class UPIPaymentServiceImpl implements UPIPaymentService {

    private final UPIPaymentDAO dao = new UPIPaymentDAOImpl();

    @Override
    public String makeUPIPayment(Transfer transfer) {
        if (!isValidUPI(transfer.getReceiverAccountNumber())) {
            transfer.setStatus(false);
            return " Invalid UPI ID.";
        }

        if (!hasSufficientFunds(transfer.getSenderAccountNumber(), transfer.getAmount())) {
            transfer.setStatus(false);
            return "Insufficient balance.";
        }

        transfer.setTransferDateTime(LocalDateTime.now());
        transfer.setTransferId(generateTransactionId());
        transfer.setStatus(true);
        dao.saveUPITransfer(transfer);

        return "UPI Payment Successful! Transaction ID: " + transfer.getTransferId();
    }

    @Override
    public List<Transfer> getTransfersBySender(String senderId) {
        return dao.getTransfersBySender(senderId); 
    }

    private boolean isValidUPI(String upiId) {
        return upiId != null && upiId.matches("^[\\w.-]+@[\\w.-]+$");
    }

    private boolean hasSufficientFunds(String senderAccount, double amount) {
        return amount <= 50000;
    }

    private int generateTransactionId() {
        return new Random().nextInt(900000) + 100000;
    }
}
