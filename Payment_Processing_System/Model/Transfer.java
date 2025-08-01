package com.ezpay.bank.model;

import java.time.LocalDateTime;

/**
 * The Transfer class represents a money transfer transaction between two bank accounts
 * in the EZPay banking system.
 */
public class Transfer {
    
    // Unique ID for the transfer transaction
    private int transferId;
    
    // Account number of the sender
    private String senderAccountNumber;
    
    // Account number of the receiver
    private String receiverAccountNumber;
    
    // Amount transferred
    private double amount;
    
    // Timestamp of when the transfer was made
    private LocalDateTime transferDateTime;
    
    // Status of the transfer: true if successful, false otherwise
    private boolean status;

    /**
     * Parameterized constructor to initialize all transfer details.
     * 
     * @param transferId             Unique identifier for the transfer
     * @param senderAccountNumber    Sender's bank account number
     * @param receiverAccountNumber  Receiver's bank account number
     * @param amount                 Amount to be transferred
     * @param transferDateTime       Date and time of transfer
     * @param status                 Status of the transaction
     */
    public Transfer(int transferId, String senderAccountNumber, String receiverAccountNumber, double amount, LocalDateTime transferDateTime, boolean status) {
        this.transferId = transferId;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.transferDateTime = transferDateTime;
        this.status = status;
    }

    // Getters

    /**
     * @return Transfer ID
     */
    public int getTransferId() {
        return transferId;
    }

    /**
     * @return Sender's account number
     */
    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    /**
     * @return Receiver's account number
     */
    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    /**
     * @return Amount transferred
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return Date and time of transfer
     */
    public LocalDateTime getTransferDateTime() {
        return transferDateTime;
    }

    /**
     * @return Status of transfer (true if successful)
     */
    public boolean isStatus() {
        return status;
    }

    // Setters

    /**
     * Set the transfer ID
     * @param transferId Transfer ID
     */
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    /**
     * Set the sender's account number
     * @param senderAccountNumber Sender's account number
     */
    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    /**
     * Set the receiver's account number
     * @param receiverAccountNumber Receiver's account number
     */
    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    /**
     * Set the amount to be transferred
     * @param amount Transfer amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Set the transfer date and time
     * @param transferDateTime Transfer timestamp
     */
    public void setTransferDateTime(LocalDateTime transferDateTime) {
        this.transferDateTime = transferDateTime;
    }

    /**
     * Set the status of the transfer
     * @param status true if successful, false otherwise
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
}
