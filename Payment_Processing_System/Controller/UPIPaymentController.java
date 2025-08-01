package com.ezpay.bank.controller;

import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.service.UPIPaymentService;
import com.ezpay.bank.service.UPIPaymentServiceImpl;

import java.util.List;

/**
 * Controller class for managing UPI payment-related operations in the EZPay system.
 * 
 * This class acts as a bridge between the user interface (UI/CLI/API) and the service layer.
 * It provides high-level methods for initiating and retrieving UPI transactions.
 */
public class UPIPaymentController {

    // Service layer dependency for handling business logic
    private final UPIPaymentService upiPaymentService = new UPIPaymentServiceImpl();

    /**
     * Initiates a new UPI payment by delegating to the service layer.
     *
     * @param transfer The Transfer object containing sender ID, receiver UPI ID, and transfer amount.
     * @return A string message indicating whether the payment was successful or failed.
     */
    public String makeUPIPayment(Transfer transfer) {
        return upiPaymentService.makeUPIPayment(transfer);
    }

    /**
     * Retrieves all UPI transactions initiated by a specific sender.
     *
     * @param senderId The sender's user ID or account number.
     * @return A list of Transfer objects representing the sender’s transaction history.
     */
    public List<Transfer> getTransfersBySender(String senderId) {
        return upiPaymentService.getTransfersBySender(senderId);
    }
}
