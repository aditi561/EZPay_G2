package com.nwg.ezpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.service.UPIAccountService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/upi/accounts")
public class UPIAccountController {

    @Autowired
    private UPIAccountService upiAccountService;

    public UPIAccountController(UPIAccountService upiAccountService) {
        super();
        this.upiAccountService = upiAccountService;
    }

    // ✅ Create new account
    @PostMapping("/create")
    public ResponseEntity<UPIAccount> createAccount(
            @RequestBody UPIAccount upiAccount) {
        UPIAccount createdAccount = upiAccountService.createAccount(upiAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    // ✅ Get all accounts
    @GetMapping("/all")
    public ResponseEntity<List<UPIAccount>> getAllAccounts() {
        List<UPIAccount> accounts = upiAccountService.getAllAccounts();
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }

    // ✅ Get account by UPI ID
    @GetMapping("/{upiId}")
    public ResponseEntity<UPIAccount> getAccount(@PathVariable String upiId) {
        Optional<UPIAccount> accountOpt = upiAccountService.getAccountByUpiId(upiId);
        return accountOpt.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ Update balance
    @PutMapping("/{upiId}/balance")
    public ResponseEntity<UPIAccount> updateBalance(
            @PathVariable String upiId,
            @RequestParam Integer newBalance) {
        try {
            UPIAccount updatedAccount = upiAccountService.updateBalance(upiId, newBalance);
            return ResponseEntity.ok(updatedAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ✅ Delete account
    @DeleteMapping("/{upiId}")
    public ResponseEntity<String> deleteAccount(@PathVariable String upiId) {
        Optional<UPIAccount> accountOpt = upiAccountService.getAccountByUpiId(upiId);
        if (accountOpt.isPresent()) {
            upiAccountService.deleteAccount(upiId);
            return ResponseEntity.ok("Account with UPI ID " + upiId + " deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Account with UPI ID " + upiId + " not found.");
    }
}
