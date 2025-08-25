package com.nwg.ezpay.service;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.entity.UPITransaction;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link UPITransactionService}.
 *
 * This class validates transaction creation, PIN verification,
 * fetching, and deletion logic using Mockito.
 *
 * Author: Aditi Roy
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

    /**
     * Setup executed before each test case.
     * Initializes mocks and test data.
     */
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

    /**
     * Test adding a valid transaction successfully.
     */
    @Test
    void testAddTransaction_Success() {
        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.of(senderAccount));
        when(upiTransactionRepository.save(any(UPITransaction.class))).thenReturn(testTransaction);

        UPITransaction saved = upiTransactionService.addUPITransaction(testTransaction);

        assertNotNull(saved);
        assertEquals("PENDING", saved.getStatus());
        verify(upiTransactionRepository, times(1)).save(testTransaction);
    }

    /**
     * Test adding transaction with invalid UPI ID throws exception.
     */
    @Test
    void testAddTransaction_InvalidUpiId() {
        testTransaction.setReceiverUpiId("invalidupi");

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> upiTransactionService.addUPITransaction(testTransaction));

        assertEquals("Invalid UPI ID format.", ex.getMessage());
    }

    /**
     * Test adding transaction with insufficient balance throws exception.
     */
    @Test
    void testAddTransaction_InsufficientBalance() {
        senderAccount.setBalance(500); // Less than transaction amount

        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.of(senderAccount));

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> upiTransactionService.addUPITransaction(testTransaction));

        assertEquals("Insufficient balance.", ex.getMessage());
    }

    // ---------- VERIFY PIN ----------

    /**
     * Test verifying correct PIN updates transaction to SUCCESS.
     */
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

    /**
     * Test verifying transaction with wrong PIN sets status to FAILED.
     */
    @Test
    void testVerifyTransactionPin_WrongPin() {
        when(upiTransactionRepository.findById(101)).thenReturn(Optional.of(testTransaction));
        when(upiAccountRepository.findByUpiId("john@upi")).thenReturn(Optional.of(senderAccount));
        when(upiTransactionRepository.save(any(UPITransaction.class))).thenReturn(testTransaction);

        UPITransaction result = upiTransactionService.verifyTransactionPin(101, "9999");

        assertEquals("FAILED", result.getStatus());
    }

    /**
     * Test verifying a non-existent transaction throws exception.
     */
    @Test
    void testVerifyTransactionPin_TransactionNotFound() {
        when(upiTransactionRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> upiTransactionService.verifyTransactionPin(999, "1234"));
    }

    // ---------- FETCH ALL ----------

    /**
     * Test fetching all transactions returns a list.
     */
    @Test
    void testShowAllTransactions() {
        when(upiTransactionRepository.findAll()).thenReturn(Arrays.asList(testTransaction));

        List<UPITransaction> result = upiTransactionService.showAllUPITransactions();

        assertEquals(1, result.size());
        verify(upiTransactionRepository, times(1)).findAll();
    }

    /**
     * Test fetching transactions returns empty list when none exist.
     */
    @Test
    void testShowAllTransactions_Empty() {
        when(upiTransactionRepository.findAll()).thenReturn(Collections.emptyList());

        List<UPITransaction> result = upiTransactionService.showAllUPITransactions();

        assertTrue(result.isEmpty());
    }

    // ---------- DELETE ----------

    /**
     * Test deleting existing transaction returns true.
     */
    @Test
    void testDeleteTransaction_Found() {
        when(upiTransactionRepository.existsById(101)).thenReturn(true);

        boolean result = upiTransactionService.deleteUPITransaction(101);

        assertTrue(result);
        verify(upiTransactionRepository, times(1)).deleteById(101);
    }

    /**
     * Test deleting non-existing transaction returns false.
     */
    @Test
    void testDeleteTransaction_NotFound() {
        when(upiTransactionRepository.existsById(999)).thenReturn(false);

        boolean result = upiTransactionService.deleteUPITransaction(999);

        assertFalse(result);
        verify(upiTransactionRepository, never()).deleteById(999);
    }

    // ---------- FIND BY ID ----------

    /**
     * Test finding transaction by ID when it exists.
     */
    @Test
    void testGetTransactionById_Found() {
        when(upiTransactionRepository.findById(101)).thenReturn(Optional.of(testTransaction));

        Optional<UPITransaction> result = upiTransactionService.getUPITransactionById(101);

        assertTrue(result.isPresent());
    }

    /**
     * Test finding transaction by ID when it does not exist.
     */
    @Test
    void testGetTransactionById_NotFound() {
        when(upiTransactionRepository.findById(999)).thenReturn(Optional.empty());

        Optional<UPITransaction> result = upiTransactionService.getUPITransactionById(999);

        assertFalse(result.isPresent());
    }

    // ---------- FIND BY STATUS ----------

    /**
     * Test fetching transactions by status returns matching list.
     */
    @Test
    void testGetTransactionsByStatus() {
        when(upiTransactionRepository.findByStatus("PENDING")).thenReturn(Arrays.asList(testTransaction));

        List<UPITransaction> result = upiTransactionService.getUPITransactionsByStatus("PENDING");

        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
    }
}
