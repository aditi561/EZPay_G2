package com.ezpay.bank.test;

import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.model.dao.UPIPaymentDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*; // Standard assert methods only

/**
 * Author: Muskan 
 * Reviewer: [Reviewer Name]
 * Date of Review: [DD-MM-YYYY]
 * 
 * Test class for UPIPaymentDAOImpl.
 * This class performs unit tests on key DAO operations including save, retrieve,
 * count, and clear operations related to UPI Transfers.
 */
public class UPIPaymentDAOTest {

    private UPIPaymentDAOImpl dao;

    /**
     * Initializes the DAO instance before each test.
     * Ensures each test runs in an isolated, clean state.
     */
    @BeforeEach
    public void setup() {
        dao = new UPIPaymentDAOImpl();
    }

    /**
     * Test case for saveUPITransfer().
     * Verifies that a transfer is successfully saved and can be retrieved.
     */
    @Test
    public void testSaveUPITransfer() {
        Transfer t1 = new Transfer("TXN1", "Alice123", "Bob456", 500.00, "2025-08-01");
        boolean result = dao.saveUPITransfer(t1);
        assertTrue(result, "Transfer should be saved successfully");

        List<Transfer> transfers = dao.getTransfersBySender("Alice123");
        assertEquals(1, transfers.size(), "Only one transfer should be stored for Alice123");
        assertEquals("Bob456", transfers.get(0).getReceiverAccountNumber(), "Receiver should match the expected value");
    }

    /**
     * Test case for getTransfersBySender().
     * Ensures that only the transfers initiated by the specified sender are returned.
     */
    @Test
    public void testGetTransfersBySender() {
        dao.saveUPITransfer(new Transfer("TXN2", "JohnDoe", "Payee789", 100.00, "2025-08-01"));
        dao.saveUPITransfer(new Transfer("TXN3", "JohnDoe", "Vendor321", 300.00, "2025-08-02"));
        dao.saveUPITransfer(new Transfer("TXN4", "SomeoneElse", "Vendor321", 150.00, "2025-08-02"));

        List<Transfer> johnTransfers = dao.getTransfersBySender("JohnDoe");
        assertEquals(2, johnTransfers.size(), "JohnDoe should have exactly 2 transfers");

        for (Transfer t : johnTransfers) {
            assertEquals("JohnDoe", t.getSenderAccountNumber(), "All transfers should belong to JohnDoe");
        }
    }
