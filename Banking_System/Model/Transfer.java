package com.ezpay.bank.model;

import java.time.LocalDateTime;

public class Transfer {
    private int transferId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private double amount;
    private LocalDateTime transferDateTime;
    private boolean status; // true if successful, false otherwise

    public Transfer(int transferId, String senderAccountNumber, String receiverAccountNumber, double amount, LocalDateTime transferDateTime, boolean status) {
        this.transferId = transferId;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.transferDateTime = transferDateTime;
        this.status = status;
    }
    //getters
    public int getTransferId() {
        return transferId;
    }
    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }
    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDateTime getTransferDateTime() {
        return transferDateTime;
    }

    //setters
    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }
    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setTransferDateTime(LocalDateTime transferDateTime) {
        this.transferDateTime = transferDateTime;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
