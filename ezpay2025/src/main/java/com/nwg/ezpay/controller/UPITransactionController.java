package com.nwg.ezpay.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nwg.ezpay.service.UPITransactionService;
import com.nwg.ezpay.entity.UPITransaction;

@RestController
@RequestMapping("upi/transactions")
public class UPITransactionController {

    private final UPITransactionService upiTransactionService;

    public UPITransactionController(UPITransactionService upiTransactionService) {
        super();
        this.upiTransactionService = upiTransactionService;
    }

    // ✅ Get all transactions
    @GetMapping("/all")
    public ResponseEntity<List<UPITransaction>> showAllUPITransactions() {
        List<UPITransaction> transactions = upiTransactionService.showAllUPITransactions();
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }

    // ✅ Create transaction
    @PostMapping("/create")
    public ResponseEntity<UPITransaction> createTransaction(@RequestBody UPITransaction upiTransaction) {
        UPITransaction createdTransaction = upiTransactionService.addUPITransaction(upiTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    // ✅ Verify transaction pin
//    @PostMapping("/{id}/verify")
//    public ResponseEntity<UPITransaction> verifyTransaction(@PathVariable Integer id, @RequestParam String pin) {
//        try {
//            UPITransaction verifiedTransaction = upiTransactionService.verifyTransactionPin(id, pin);
//            return ResponseEntity.ok(verifiedTransaction);
//        } catch (RuntimeException e) {
//        	System.out.println(e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
    
    @PostMapping("/{id}/verify")
    public ResponseEntity<?> verifyTransaction(@PathVariable Integer id, @RequestParam String pin) {
        try {
            UPITransaction verifiedTransaction = upiTransactionService.verifyTransactionPin(id, pin);
            return ResponseEntity.ok(verifiedTransaction);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }


    // ✅ Delete transaction
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable int id) {
        boolean deleted = upiTransactionService.deleteUPITransaction(id);
        if (deleted) {
            return ResponseEntity.ok("Transaction with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Transaction with ID " + id + " not found.");
        }
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<UPITransaction> getTransactionById(@PathVariable int id) {
        return upiTransactionService.getUPITransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ Get by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<UPITransaction>> getTransactionsByStatus(@PathVariable String status) {
        List<UPITransaction> transactions = upiTransactionService.getUPITransactionsByStatus(status);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }
}
