package com.ezpay.bank.service;

import com.ezpay.bank.model.Transfer;

public interface UPIPaymentService {
    String makeUPIPayment(Transfer transfer);
    List<Transfer> getTransfersBySender(String senderId);

}