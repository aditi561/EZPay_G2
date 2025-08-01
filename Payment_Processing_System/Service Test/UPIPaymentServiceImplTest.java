package com.ezpay.bank.test;

import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.model.dao.UPIPaymentDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*; 

/**
 * @author Muskan
 * @version 0.0.1
 * @since 2025-07-30
 * @revised 2025-08-01

 * Test class for UPIPaymentDAOImpl.
 * This class performs unit tests on key DAO operations including save and retrieve operations related to UPI Transfers.
 */
public class UPIPaymentDAOTest {

    private UPIPaymentDAO dao;

    /**
     * Initialize a fresh instance of DAO before each test.
     */
    @BeforeEach
    public void setUp() {
        dao = new UPIPaymentDAOImpl(); // using in-memory list implementation
    }

    /**
     * Test saving a valid transfer.
     * Ensures that a well-formed transfer is saved successfully.
     */
    @Test
    public void testSaveUPITransfer() {
        Transfer transfer = new Transfer("TXN123", "Alice", "Bob", 100.0, "2025-07-29");
        boolean result = dao.saveUPITransfer(transfer);
        assertTrue(result, "Transfer should be saved successfully");
    }

    /**
     * Test retrieving transfers by a valid sender.
     * Verifies that only transfers sent by "Alice" are returned.
     */
    @Test
    public void testGetTransfersBySender() {
        dao.saveUPITransfer(new Transfer("TXN123", "Alice", "Bob", 100.0, "2025-07-29"));
        dao.saveUPITransfer(new Transfer("TXN124", "Charlie", "Alice", 50.0, "2025-07-29"));
        dao.saveUPITransfer(new Transfer("TXN125", "Alice", "David", 75.0, "2025-07-29"));

        List<Transfer> transfers = dao.getTransfersBySender("Alice");
        assertEquals(2, transfers.size(), "Alice should have 2 outgoing transfers");
        for (Transfer t : transfers) {
            assertEquals("Alice", t.getSender(), "Sender should be Alice");
        }
    }

    /**
     * Test saving a null transfer.
     * DAO should gracefully reject a null transfer object.
     */
    @Test
    public void testSaveNullTransfer() {
        boolean result = dao.saveUPITransfer(null);
        assertFalse(result, "Saving null transfer should return false or fail gracefully");
    }

    /**
     * Test retrieving transfers by a sender who has no transactions.
     * DAO should return an empty list.
     */
    @Test
    public void testGetTransfersByNonExistentSender() {
        List<Transfer> transfers = dao.getTransfersBySender("GhostSender");
        assertNotNull(transfers, "Result should not be null");
        assertTrue(transfers.isEmpty(), "No transfers should be returned for unknown sender");
    }

    /**
     * Test getTransfersBySender with empty or null sender string.
     * Should return an empty list and not throw exceptions.
     */
    @Test
    public void testGetTransfersByEmptyOrNullSender() {
        List<Transfer> emptySenderTransfers = dao.getTransfersBySender("");
        List<Transfer> nullSenderTransfers = dao.getTransfersBySender(null);

        assertNotNull(emptySenderTransfers, "Should return non-null list for empty sender");
        assertTrue(emptySenderTransfers.isEmpty(), "Empty sender should yield no results");

        assertNotNull(nullSenderTransfers, "Should return non-null list for null sender");
        assertTrue(nullSenderTransfers.isEmpty(), "Null sender should yield no results");
    }

    /**
     * Test saving a transfer with negative amount.
     * This should be rejected by the DAO.
     */
    @Test
    public void testNegativeAmountTransfer() {
        Transfer transfer = new Transfer("TXN_NEG", "NegSender", "NegReceiver", -100.0, "2025-08-01");
        boolean result = dao.saveUPITransfer(transfer);
        assertFalse(result, "Negative amount transfers should be rejected");
    }

    /**
     * Test saving a transfer with a very large amount.
     * Should succeed if no max cap is enforced in the DAO.
     */
    @Test
    public void testLargeAmountTransfer() {
        Transfer transfer = new Transfer("TXN_LARGE", "RichieRich", "GoldSeller", 1_000_000_000.0, "2025-08-01");
        boolean result = dao.saveUPITransfer(transfer);
        assertTrue(result, "Large amount transfer should be saved");

        List<Transfer> transfers = dao.getTransfersBySender("RichieRich");
        assertEquals(1, transfers.size());
        assertEquals(1_000_000_000.0, transfers.get(0).getAmount());
    }

    /**
     * Test saving a transfer with null sender.
     * DAO should reject such transfers.
     */
    @Test
    public void testTransferWithNullSender() {
        Transfer transfer = new Transfer("TXN_NULL", null, "ReceiverOnly", 50.0, "2025-08-01");
        boolean result = dao.saveUPITransfer(transfer);
        assertFalse(result, "Transfers with null sender should be rejected");
    }

    /**
     * Test saving a transfer with null receiver.
     * DAO should reject such transfers.
     */
    @Test
    public void testTransferWithNullReceiver() {
        Transfer transfer = new Transfer("TXN_NULL2", "SenderOnly", null, 50.0, "2025-08-01");
        boolean result = dao.saveUPITransfer(transfer);
        assertFalse(result, "Transfers with null receiver should be rejected");
    }
}
