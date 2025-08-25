package com.nwg.ezpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.repository.UPIAccountRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing UPI account operations.
 * Handles business logic for creating, reading, updating, and deleting UPI
 * accounts.
 */
@Service
public class UPIAccountService {

    /** Repository for UPI account data access operations */
    @Autowired
    private UPIAccountRepository upiAccountRepository;

    /**
     * Constructor for dependency injection
     * 
     * @param upiAccountRepository repository for UPI account operations
     */
    public UPIAccountService(UPIAccountRepository upiAccountRepository) {
        super();
        this.upiAccountRepository = upiAccountRepository;
    }

    /**
     * Creates a new UPI account
     * 
     * @param upiAccount the account details to create
     * @return the created UPI account
     */
    public UPIAccount createAccount(UPIAccount upiAccount) {
        return upiAccountRepository.save(upiAccount);
    }

    /**
     * Retrieves all UPI accounts
     * 
     * @return list of all UPI accounts
     */
    public List<UPIAccount> getAllAccounts() {
        return upiAccountRepository.findAll();
    }

    /**
     * Retrieves a UPI account by its UPI ID
     * 
     * @param upiId the UPI ID to search for
     * @return Optional containing the account if found, empty otherwise
     */
    public Optional<UPIAccount> getAccountByUpiId(String upiId) {
        return upiAccountRepository.findByUpiId(upiId);
    }

    /**
     * Updates the balance of a UPI account
     * 
     * @param upiId      the UPI ID of the account to update
     * @param newBalance the new balance to set
     * @return the updated UPI account
     * @throws RuntimeException if account is not found
     */
    public UPIAccount updateBalance(String upiId, Integer newBalance) {
        Optional<UPIAccount> accountOpt = upiAccountRepository.findByUpiId(upiId);
        if (accountOpt.isPresent()) {
            UPIAccount account = accountOpt.get();
            account.setBalance(newBalance);
            return upiAccountRepository.save(account);
        }
        throw new RuntimeException("Account not found for UPI ID: " + upiId);
    }

    /**
     * Deletes a UPI account if it exists
     * 
     * @param upiId the UPI ID of the account to delete
     */
    public void deleteAccount(String upiId) {
        Optional<UPIAccount> accountOpt = upiAccountRepository.findByUpiId(upiId);
        accountOpt.ifPresent(upiAccountRepository::delete);
    }

    /*
     * Commented PIN utility methods
     * private String hashPin(String plainPin) {
     * return BCrypt.hashpw(plainPin, BCrypt.gensalt());
     * }
     * 
     * public boolean verifyPin(String plainPin, String pinHash) {
     * return BCrypt.checkpw(plainPin, pinHash);
     * }
     */
}