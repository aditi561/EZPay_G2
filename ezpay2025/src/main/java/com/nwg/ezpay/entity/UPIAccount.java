package com.nwg.ezpay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Entity class representing a UPI (Unified Payments Interface) account.
 * This class maps to the 'upi_accounts' table in the database.
 */
@Entity
@Table(name = "upi_accounts")
public class UPIAccount {

	/** Unique identifier for the UPI account */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** Unique UPI ID associated with the account */
	@Column(name = "UPI_ID", unique = true, nullable = false)
	private String upiId;

	/** Current balance in the account */
	@Column(name = "BALANCE", nullable = false, precision = 15, scale = 2)
	private Integer balance;

	/** PIN for securing transactions */
	@Column(name = "PIN", nullable = false)
	private String pin;

	/** Flag indicating if the account is active */
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive = true;

	/** Timestamp when the account was created */
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt = LocalDateTime.now();

	/**
	 * Default constructor
	 */
	public UPIAccount() {
	}

	/**
	 * Parameterized constructor to create a UPI account
	 * 
	 * @param id        Unique identifier
	 * @param upiId     UPI ID for the account
	 * @param balance   Initial balance
	 * @param isActive  Account status
	 * @param createdAt Creation timestamp
	 * @param pin       Security PIN
	 */
	public UPIAccount(Long id, String upiId, Integer balance, boolean isActive,
			LocalDateTime createdAt, String pin) {
		this.id = id;
		this.upiId = upiId;
		this.balance = balance;
		this.pin = pin;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	/**
	 * @return the account ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the account ID to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the UPI ID
	 */
	public String getUpiId() {
		return upiId;
	}

	/**
	 * @param upiId the UPI ID to set
	 */
	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	/**
	 * @return the current balance
	 */
	public Integer getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	/**
	 * @return the PIN
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin the PIN to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @return true if account is active, false otherwise
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the active status to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the creation timestamp
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the creation timestamp to set
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
