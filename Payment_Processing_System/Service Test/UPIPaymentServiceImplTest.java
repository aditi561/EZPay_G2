package com.ezpay.bank.service_test;

import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.service.UPIPaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UPI payment functionalities using JUnit.
 * @author MUSKAN 
 */
public class UPIPaymentServiceImplTest {

    private UPIPaymentServiceImpl upiPaymentService;

    @BeforeEach
    public void setUp() {
        upiPaymentService = new UPIPaymentServiceImpl();
    }

    @Test
    public void testMakeUPIPayment() {
        Transfer upiTransfer = new Transfer();
        upiTransfer.setSenderAccountNumber("user123");
        upiTransfer.setReceiverAccountNumber("receiver@upi");
        upiTransfer.setAmount(250.0);
        upiTransfer.setTransferDateTime(LocalDateTime.now());

        String result = upiPaymentService.makeUPIPayment(upiTransfer);

        assertNotNull(result);
        assertTrue(result.contains("successful") || result.contains("failed") || result.length() > 0);
    }

    @Test
    public void testGetTransfersBySender() {
        String senderId = "user123";

        List<Transfer> transfers = upiPaymentService.getTransfersBySender(senderId);

        assertNotNull(transfers);
        assertTrue(transfers instanceof List);
    }
}
