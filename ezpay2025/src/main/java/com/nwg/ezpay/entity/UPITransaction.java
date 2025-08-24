package com.nwg.ezpay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "upi_transactions")
public class UPITransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SENDER_UPI_ID",nullable = false)
    private String senderUpiId;

    @Column(name = "RECEIVER_UPI_ID",nullable = false)
    private String receiverUpiId;

    @Column(name = "AMOUNT",nullable = false, precision = 15, scale = 2)
    private Integer amount;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "STATUS",nullable = false)
    private String status;   // SUCCESS / FAILED

    @Column(name = "CREATED_AT")
    private LocalDateTime timestamp = LocalDateTime.now();
    
    public UPITransaction() {
    	
    };

    public UPITransaction(Long id, String senderUpiId, String receiverUpiId, Integer amount, String remarks,
			String status, LocalDateTime timestamp) {
		this.id = id;
		this.senderUpiId = senderUpiId;
		this.receiverUpiId = receiverUpiId;
		this.amount = amount;
		this.remarks = remarks;
		this.status = status;
		this.timestamp = timestamp;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderUpiId() {
        return senderUpiId;
    }

    public void setSenderUpiId(String senderUpiId) {
        this.senderUpiId = senderUpiId;
    }

    public String getReceiverUpiId() {
        return receiverUpiId;
    }

    public void setReceiverUpiId(String receiverUpiId) {
        this.receiverUpiId = receiverUpiId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}