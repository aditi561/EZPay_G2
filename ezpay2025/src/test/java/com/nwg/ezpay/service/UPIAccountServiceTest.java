package com.nwg.ezpay.service;

import com.nwg.ezpay.entity.UPIAccount;
import com.nwg.ezpay.repository.UPIAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
 * Unit tests for {@link UPIAccountService}.
 * 
 * This class tests CRUD operations for UPIAccountService
 * using Mockito to mock repository interactions.
 *
 * Author: Aditi Roy
 */
class UPIAccountServiceTest {

    @Mock
    private UPIAccountRepository upiAccountRepository; // Mocked repository dependency

    @InjectMocks
    private UPIAccountService upiAccountService; // Service under test

    private UPIAccount testAccount; // Sample UPI account for testing

    /**
     * Setup runs before each test case.
     * Initializes Mockito and creates a sample UPI account.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testAccount = new UPIAccount();
        testAccount.setId(1L);
        testAccount.setUpiId(" john@upi");
        testAccount.setBalance(5000);
    }

    // ---------- CREATE ----------

    /**
     * Test successful creation of a UPI account.
     */
    @Test
    void testCreateAccount_Success() {
        when(upiAccountRepository.save(testAccount)).thenReturn(testAccount);

        UPIAccount saved = upiAccountService.createAccount(testAccount);

        assertNotNull(saved);
        assertEquals(" john@upi", saved.getUpiId());
        verify(upiAccountRepository, times(1)).save(testAccount);
    }

    /**
     * Test creation with null object throws exception.
     */
    @Test
    void testCreateAccount_NullObject() {
        when(upiAccountRepository.save(null)).thenThrow(new IllegalArgumentException("Entity must not be null"));

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> upiAccountService.createAccount(null));

        assertEquals("Entity must not be null", ex.getMessage());
    }

    // ---------- GET ALL ----------

    /**
     * Test fetching all accounts returns a non-empty list.
     */
    @Test
    void testGetAllAccounts() {
        when(upiAccountRepository.findAll()).thenReturn(Arrays.asList(testAccount));

        List<UPIAccount> accounts = upiAccountService.getAllAccounts();

        assertEquals(1, accounts.size());
        assertEquals(" john@upi", accounts.get(0).getUpiId());
        verify(upiAccountRepository, times(1)).findAll();
    }

    /**
     * Test fetching all accounts returns empty list when no data exists.
     */
    @Test
    void testGetAllAccounts_EmptyList() {
        when(upiAccountRepository.findAll()).thenReturn(Collections.emptyList());

        List<UPIAccount> accounts = upiAccountService.getAllAccounts();

        assertTrue(accounts.isEmpty());
        verify(upiAccountRepository, times(1)).findAll();
    }

    // ---------- GET BY UPI ID ----------

    /**
     * Test fetching account by UPI ID when account exists.
     */
    @Test
    void testGetAccountByUpiId_Found() {
        when(upiAccountRepository.findByUpiId(" john@upi")).thenReturn(Optional.of(testAccount));

        Optional<UPIAccount> result = upiAccountService.getAccountByUpiId(" john@upi");

        assertTrue(result.isPresent());
        assertEquals(5000, result.get().getBalance());
        verify(upiAccountRepository, times(1)).findByUpiId(" john@upi");
    }

    /**
     * Test fetching account by UPI ID when account does not exist.
     */
    @Test
    void testGetAccountByUpiId_NotFound() {
        when(upiAccountRepository.findByUpiId("xyz@upi")).thenReturn(Optional.empty());

        Optional<UPIAccount> result = upiAccountService.getAccountByUpiId("xyz@upi");

        assertFalse(result.isPresent());
    }

    /**
     * Test fetching account with null UPI ID throws exception.
     */
    @Test
    void testGetAccountByUpiId_NullId() {
        when(upiAccountRepository.findByUpiId(null)).thenThrow(new IllegalArgumentException("UPI ID cannot be null"));

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> upiAccountService.getAccountByUpiId(null));

        assertEquals("UPI ID cannot be null", ex.getMessage());
    }

    // ---------- UPDATE BALANCE ----------

    /**
     * Test successful update of account balance.
     */
    @Test
    void testUpdateBalance_Success() {
        when(upiAccountRepository.findByUpiId(" john@upi")).thenReturn(Optional.of(testAccount));
        when(upiAccountRepository.save(any(UPIAccount.class))).thenReturn(testAccount);

        UPIAccount updated = upiAccountService.updateBalance(" john@upi", 7000);

        assertEquals(7000, updated.getBalance());
        verify(upiAccountRepository, times(1)).save(testAccount);
    }

    /**
     * Test updating balance for non-existing account throws exception.
     */
    @Test
    void testUpdateBalance_NotFound() {
        when(upiAccountRepository.findByUpiId("xyz@upi")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> upiAccountService.updateBalance("xyz@upi", 7000));

        assertEquals("Account not found for UPI ID: xyz@upi", ex.getMessage());
    }

    /**
     * Test updating balance with null UPI ID throws exception.
     */
    @Test
    void testUpdateBalance_NullId() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> upiAccountService.updateBalance(null, 500));

        assertEquals("Account not found for UPI ID: null", ex.getMessage());
    }

    // ---------- DELETE ----------

    /**
     * Test successful deletion of account by UPI ID.
     */
    @Test
    void testDeleteAccount_Found() {
        when(upiAccountRepository.findByUpiId(" john@upi")).thenReturn(Optional.of(testAccount));

        upiAccountService.deleteAccount(" john@upi");

        verify(upiAccountRepository, times(1)).delete(testAccount);
    }

    /**
     * Test deleting non-existing account does nothing.
     */
    @Test
    void testDeleteAccount_NotFound() {
        when(upiAccountRepository.findByUpiId("xyz@upi")).thenReturn(Optional.empty());

        upiAccountService.deleteAccount("xyz@upi");

        verify(upiAccountRepository, never()).delete(any(UPIAccount.class));
    }

    /**
     * Test deleting account with null UPI ID throws exception.
     */
    @Test
    void testDeleteAccount_NullId() {
        when(upiAccountRepository.findByUpiId(null)).thenThrow(new IllegalArgumentException("UPI ID cannot be null"));

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> upiAccountService.deleteAccount(null));

        assertEquals("UPI ID cannot be null", ex.getMessage());
    }
}
