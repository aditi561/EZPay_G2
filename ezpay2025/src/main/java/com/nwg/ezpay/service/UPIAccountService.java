package com.nwg.ezpay.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.repository.UPIAccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UPIAccountService {

    @Autowired
    private UPIAccountRepository upiAccountRepository;
    
    public UPIAccountService(UPIAccountRepository upiAccountRepository) {
    	super();
    	this.upiAccountRepository = upiAccountRepository;
    }

    // Create Account (hashes the PIN before saving)
    public UPIAccount createAccount(UPIAccount upiAccount) {
        return upiAccountRepository.save(upiAccount);
    }

    // Get all accounts
    public List<UPIAccount> getAllAccounts() {
        return upiAccountRepository.findAll();
    }

    // Get account by UPI ID
    public Optional<UPIAccount> getAccountByUpiId(String upiId) {
        return upiAccountRepository.findByUpiId(upiId);
    }

    // Update account balance
    public UPIAccount updateBalance(String upiId, Integer newBalance) {
        Optional<UPIAccount> accountOpt = upiAccountRepository.findByUpiId(upiId);
        if (accountOpt.isPresent()) {
            UPIAccount account = accountOpt.get();
            account.setBalance(newBalance);
            return upiAccountRepository.save(account);
        }
        throw new RuntimeException("Account not found for UPI ID: " + upiId);
    }

    // Delete account
    public void deleteAccount(String upiId) {
        Optional<UPIAccount> accountOpt = upiAccountRepository.findByUpiId(upiId);
        accountOpt.ifPresent(upiAccountRepository::delete);
    }

//    // --- Utility methods for PIN ---
//    private String hashPin(String plainPin) {
//        return BCrypt.hashpw(plainPin, BCrypt.gensalt());
//    }
//
//    public boolean verifyPin(String plainPin, String pinHash) {
//        return BCrypt.checkpw(plainPin, pinHash);
//    }
}