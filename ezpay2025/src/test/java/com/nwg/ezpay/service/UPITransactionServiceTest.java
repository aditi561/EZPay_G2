package com.nwg.ezpay.service;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.entity.UPITransaction;
import com.nwg.ezpay.exception.AccountNotFoundException;
import com.nwg.ezpay.exception.InsufficientBalanceException;
import com.nwg.ezpay.exception.InvalidAmountException;
import com.nwg.ezpay.exception.InvalidPinException;
import com.nwg.ezpay.exception.TransactionNotFoundException;
import com.nwg.ezpay.repository.UPIAccountRepository;
import com.nwg.ezpay.repository.UPITransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UPITransactionService}.
 *
 * Validates transaction creation, PIN verification,
 * fetching, and deletion logic using Mockito.
 *
 * Author: Aditi Roy (edited for corrected service)
 */
class UPITransactionServiceTest {

    @Mock
    private UPITransactionRepository upiTransactionRepository;

    @Mock
    private UPIAccountRepository upiAccountRepository;

    @InjectMocks
    private UPITransactionService upiTransactionService;

    private UPIAccount senderAccount;
    private UPITransaction testTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        senderAccount = new UPIAccount();
        senderAccount.setId(1L);
        senderAccount.setUpiId("john@upi");
        senderAccount.setBalance(5000);
        senderAccount.setPin("1234");

        testTransaction = new UPITransaction();
        testTransaction.setSenderUpiId("john@upi");
        testTransaction.setReceiverUpiId("receiver@upi");
        testTransaction.setAmount(1000);
        testTransaction.setStatus("PENDING");
        testTransaction.setTimestamp(LocalDateTime.now());
    }

    // ---------- ADD TRANSACTION ----------

    @Test
    void testAddTransaction_Success() {
        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.of(senderAccount));
        when(upiTransactionRepository.save(any(UPITransaction.class))).thenReturn(testTransaction);

        UPITransaction saved = upiTransactionService.addUPITransaction(testTransaction);

        assertNotNull(saved);
        assertEquals("PENDING", saved.getStatus());
        verify(upiTransactionRepository, times(1)).save(testTransaction);
    }

    @Test
    void testAddTransaction_InvalidUpiId() {
        testTransaction.setReceiverUpiId("invalidupi");

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> upiTransactionService.addUPITransaction(testTransaction));

        assertEquals("Invalid UPI ID format.", ex.getMessage());
    }

    @Test
    void testAddTransaction_InvalidAmount() {
        testTransaction.setAmount(0);

        InvalidAmountException ex = assertThrows(InvalidAmountException.class,
                () -> upiTransactionService.addUPITransaction(testTransaction));

        assertEquals("Amount must be greater than zero.", ex.getMessage());
    }

    @Test
    void testAddTransaction_AccountNotFound() {
        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.empty());

        AccountNotFoundException ex = assertThrows(AccountNotFoundException.class,
                () -> upiTransactionService.addUPITransaction(testTransaction));

        assertTrue(ex.getMessage().contains("Sender account not found for UPI ID"));
    }

    @Test
    void testAddTransaction_InsufficientBalance() {
        senderAccount.setBalance(500); // Less than transaction amount
        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.of(senderAccount));

        InsufficientBalanceException ex = assertThrows(InsufficientBalanceException.class,
                () -> upiTransactionService.addUPITransaction(testTransaction));

        assertEquals("Insufficient balance in sender's account.", ex.getMessage());
    }

    // ---------- VERIFY PIN ----------

    @Test
    void testVerifyTransactionPin_Success() {
        when(upiTransactionRepository.findById(101)).thenReturn(Optional.of(testTransaction));
        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.of(senderAccount));
        when(upiTransactionRepository.save(any(UPITransaction.class))).thenReturn(testTransaction);
        when(upiAccountRepository.save(any(UPIAccount.class))).thenReturn(senderAccount);

        UPITransaction result = upiTransactionService.verifyTransactionPin(101, "1234");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals(4000, senderAccount.getBalance()); // 5000 - 1000
    }

    @Test
    void testVerifyTransactionPin_WrongPin() {
        when(upiTransactionRepository.findById(101)).thenReturn(Optional.of(testTransaction));
        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.of(senderAccount));

        InvalidPinException ex = assertThrows(InvalidPinException.class,
                () -> upiTransactionService.verifyTransactionPin(101, "9999"));

        assertEquals("Invalid PIN provided", ex.getMessage());
    }

    @Test
    void testVerifyTransactionPin_TransactionNotFound() {
        when(upiTransactionRepository.findById(999)).thenReturn(Optional.empty());

        TransactionNotFoundException ex = assertThrows(TransactionNotFoundException.class,
                () -> upiTransactionService.verifyTransactionPin(999, "1234"));

        assertEquals("Transaction not found", ex.getMessage());
    }

    @Test
    void testVerifyTransactionPin_InsufficientBalance() {
        testTransaction.setAmount(6000); // more than balance
        when(upiTransactionRepository.findById(101)).thenReturn(Optional.of(testTransaction));
        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.of(senderAccount));

        InsufficientBalanceException ex = assertThrows(InsufficientBalanceException.class,
                () -> upiTransactionService.verifyTransactionPin(101, "1234"));

        assertEquals("Insufficient balance in sender's account", ex.getMessage());
    }

    // ---------- FETCH ALL ----------

    @Test
    void testShowAllTransactions() {
        when(upiTransactionRepository.findAll()).thenReturn(Arrays.asList(testTransaction));

        List<UPITransaction> result = upiTransactionService.showAllUPITransactions();

        assertEquals(1, result.size());
        verify(upiTransactionRepository, times(1)).findAll();
    }

    @Test
    void testShowAllTransactions_Empty() {
        when(upiTransactionRepository.findAll()).thenReturn(Collections.emptyList());

        List<UPITransaction> result = upiTransactionService.showAllUPITransactions();

        assertTrue(result.isEmpty());
    }

    // ---------- DELETE ----------

    @Test
    void testDeleteTransaction_Found() {
        when(upiTransactionRepository.existsById(101)).thenReturn(true);

        boolean result = upiTransactionService.deleteUPITransaction(101);

        assertTrue(result);
        verify(upiTransactionRepository, times(1)).deleteById(101);
    }

    @Test
    void testDeleteTransaction_NotFound() {
        when(upiTransactionRepository.existsById(999)).thenReturn(false);

        boolean result = upiTransactionService.deleteUPITransaction(999);

        assertFalse(result);
        verify(upiTransactionRepository, never()).deleteById(999);
    }

    // ---------- FIND BY ID ----------

    @Test
    void testGetTransactionById_Found() {
        when(upiTransactionRepository.findById(101)).thenReturn(Optional.of(testTransaction));

        Optional<UPITransaction> result = upiTransactionService.getUPITransactionById(101);

        assertTrue(result.isPresent());
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(upiTransactionRepository.findById(999)).thenReturn(Optional.empty());

        Optional<UPITransaction> result = upiTransactionService.getUPITransactionById(999);

        assertFalse(result.isPresent());
    }

    // ---------- FIND BY STATUS ----------

    @Test
    void testGetTransactionsByStatus() {
        when(upiTransactionRepository.findByStatus("PENDING")).thenReturn(Arrays.asList(testTransaction));

        List<UPITransaction> result = upiTransactionService.getUPITransactionsByStatus("PENDING");

        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
    }
}
