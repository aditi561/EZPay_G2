package com.nwg.ezpay.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.entity.UPITransaction;
import com.nwg.ezpay.repository.UPIAccountRepository;
import com.nwg.ezpay.repository.UPITransactionRepository;

@Service
public class UPITransactionService {
	@Autowired
	private UPITransactionRepository upiTransactionRepository;
	
	@Autowired
	private UPIAccountRepository upiAccountRepository;
	
	public UPITransactionService(UPITransactionRepository upiTransactionRepository) {
		super();
		this.upiTransactionRepository = upiTransactionRepository;
	}
	
    public UPITransaction addUPITransaction(UPITransaction upiTransaction) {
        if (upiTransaction.getReceiverUpiId() == null ||
            !upiTransaction.getReceiverUpiId().matches("^[a-zA-Z0-9._-]+@[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid UPI ID format.");
        }

        if (upiTransaction.getAmount() == null || upiTransaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        UPIAccount sender = upiAccountRepository.findByUpiId(upiTransaction.getSenderUpiId())
                .orElseThrow(() -> new IllegalArgumentException("No account found for given UPI ID."));

        if (upiTransaction.getAmount().compareTo(sender.getBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        upiTransaction.setStatus("PENDING");
        upiTransaction.setTimestamp(LocalDateTime.now());

        return upiTransactionRepository.save(upiTransaction);
    }

//    public UPITransaction verifyTransactionPin(Integer transactionId, String pin) {
//        UPITransaction transaction = upiTransactionRepository.findById(transactionId)
//                .orElseThrow(() -> new IllegalArgumentException("Transaction not found."));
//
//        if (!"PENDING".equals(transaction.getStatus())) {
//            throw new IllegalStateException("Transaction already processed.");
//        }
//
//        UPIAccount sender = upiAccountRepository.findByUpiId(transaction.getSenderUpiId())
//                .orElseThrow(() -> new IllegalArgumentException("Account not found."));
//
//        if (!sender.getPin().equals(pin)) {
//            transaction.setStatus("FAILED");
//            return upiTransactionRepository.save(transaction);
//        }
//
//        sender.setBalance(sender.getBalance() - transaction.getAmount());
//        upiAccountRepository.save(sender);
//
//        transaction.setStatus("SUCCESS");
//        return upiTransactionRepository.save(transaction);
//    }
//	
    
    public UPITransaction verifyTransactionPin(Integer transactionId, String pin) {
        UPITransaction transaction = upiTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found."));

        if (!"PENDING".equals(transaction.getStatus())) {
            throw new IllegalStateException("Transaction already processed.");
        }

        UPIAccount sender = upiAccountRepository.findByUpiId(transaction.getSenderUpiId())
                .orElseThrow(() -> new NoSuchElementException("Sender account not found."));

        // ✅ Compare with hashed PIN if you store it hashed
        if (!sender.getPin().equals(pin)) {
            transaction.setStatus("FAILED");
            return upiTransactionRepository.save(transaction);
        }

        // ✅ Balance check
        if (sender.getBalance() < transaction.getAmount()) {
            transaction.setStatus("FAILED");
            return upiTransactionRepository.save(transaction);
        }

        // Deduct money
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        upiAccountRepository.save(sender);

        // Update transaction
        transaction.setStatus("SUCCESS");
        return upiTransactionRepository.save(transaction);
    }

	//get all transactions
	public List<UPITransaction> showAllUPITransactions() {
		return upiTransactionRepository.findAll();
	}

    // Delete transaction
    public boolean deleteUPITransaction(int id) {
        if (upiTransactionRepository.existsById(id)) {
            upiTransactionRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    //Find By Transaction id
    public Optional<UPITransaction> getUPITransactionById(int id) {
        return upiTransactionRepository.findById(id);
    }

    // Find by status
    public List<UPITransaction> getUPITransactionsByStatus(String status) {
        return upiTransactionRepository.findByStatus(status);
    }
}
