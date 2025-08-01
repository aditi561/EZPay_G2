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
 * Test class for UPIPaymentDAOImpl
 */
public class UPIPaymentDAOTest {

    private UPIPaymentDAOImpl dao;

    // Code Indentation, Class Members: All consistent
    @BeforeEach
    public void setup() {
        dao = new UPIPaymentDAOImpl();
    }

    @Test
    public void testSaveUPITransfer() {
        Transfer t1 = new Transfer("TXN1", "Alice123", "Bob456", 500.00, "2025-08-01");
        boolean result = dao.saveUPITransfer(t1);
        assertTrue(result, "Transfer should be saved successfully");

        List<Transfer> transfers = dao.getTransfersBySender("Alice123");
        assertEquals(1, transfers.size());
        assertEquals("Bob456", transfers.get(0).getReceiverAccountNumber());
    }

    @Test
    public void testGetTransfersBySender() {
        dao.saveUPITransfer(new Transfer("TXN2", "JohnDoe", "Payee789", 100.00, "2025-08-01"));
        dao.saveUPITransfer(new Transfer("TXN3", "JohnDoe", "Vendor321", 300.00, "2025-08-02"));
        dao.saveUPITransfer(new Transfer("TXN4", "SomeoneElse", "Vendor321", 150.00, "2025-08-02"));

        List<Transfer> johnTransfers = dao.getTransfersBySender("JohnDoe");
        assertEquals(2, johnTransfers.size());

        for (Transfer t : johnTransfers) {
            assertEquals("JohnDoe", t.getSenderAccountNumber());
        }
    }

    @Test
    public void testGetTotalTransfers() {
        assertEquals(0, dao.getTotalTransfers());

        dao.saveUPITransfer(new Transfer("TXN5", "A", "B", 10.0, "2025-08-01"));
        dao.saveUPITransfer(new Transfer("TXN6", "C", "D", 20.0, "2025-08-02"));

        assertEquals(2, dao.getTotalTransfers());
    }

    @Test
    public void testClearAllTransfers() {
        dao.saveUPITransfer(new Transfer("TXN7", "A1", "B1", 15.0, "2025-08-01"));
        assertEquals(1, dao.getTotalTransfers());

        dao.clearAllTransfers();
        assertEquals(0, dao.getTotalTransfers());
    }
}
