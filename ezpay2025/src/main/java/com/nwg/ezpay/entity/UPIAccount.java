package com.nwg.ezpay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "upi_accounts")
public class UPIAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "UPI_ID" , unique = true, nullable = false)
    private String upiId;
    
    @Column(name = "BALANCE" , nullable = false, precision = 15, scale = 2)
    private Integer balance;
    
    @Column(name = "PIN",nullable = false)
    private String pin;
    
    @Column(name = "IS_ACTIVE" , nullable = false)
    private boolean isActive = true;
    
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public UPIAccount() {
    	
    };

	public UPIAccount(Long id, String upiId, Integer balance, boolean isActive,
			LocalDateTime createdAt, String pin) {
		this.id = id;
		this.upiId = upiId;
		this.balance = balance;
		this.pin = pin;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
