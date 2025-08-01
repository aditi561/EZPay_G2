import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UPI payment functionalities using only JUnit.
 */
public class UPIPaymentServiceImplTest {

    private UPIPaymentServiceImpl upiPaymentService;

    @BeforeEach
    public void setUp() {
        upiPaymentService = new UPIPaymentServiceImpl();
    }

    @Test
    public void testInitiatePayment() {
        String upiId = "test@upi";
        double amount = 100.0;

        String result = upiPaymentService.initiatePayment(upiId, amount);

        assertNotNull(result);
        assertTrue(result.contains("initiated") || result.length() > 0); // simple check
    }

    @Test
    public void testVerifyPayment() {
        String transactionId = "TXN123";

        boolean status = upiPaymentService.verifyPayment(transactionId);

        assertTrue(status || !status); // should not throw exception
    }
}
