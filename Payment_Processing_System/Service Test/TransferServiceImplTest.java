import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TransferServiceImpl using only JUnit.
 */
public class TransferServiceImplTest {

    private TransferServiceImpl transferService;

    @BeforeEach
    public void setUp() {
        transferService = new TransferServiceImpl();
    }

    @Test
    public void testTransferMoney() {
        String fromAccount = "ACC001";
        String toAccount = "ACC002";
        double amount = 500.0;

        String result = transferService.transferMoney(fromAccount, toAccount, amount);

        assertNotNull(result);
        assertTrue(result.contains("success") || result.contains("Success")); // simple success check
    }

    @Test
    public void testGetTransactionHistory() {
        String userId = "user001";

        // Assuming transaction history exists or the method returns empty list
        var history = transferService.getTransactionHistory(userId);

        assertNotNull(history); // Ensure the result is not null
    }
}
