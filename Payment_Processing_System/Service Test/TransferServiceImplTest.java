import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for TransferServiceImpl.
 * Simulates account-to-account transfers like those in Main.java.
 */
public class TransferServiceImplTest {

    private BankAccountServiceImpl fromAccount;
    private BankAccountServiceImpl toAccount;
    private TransferServiceImpl transferService;

    @BeforeEach
    public void setUp() {
        fromAccount = new BankAccountServiceImpl();
        toAccount = new BankAccountServiceImpl();
        transferService = new TransferServiceImpl();

        // Both accounts start with 1000 balance as in Main.java
        fromAccount.deposit(1000);
        toAccount.deposit(1000);
    }

    /**
     * Tests a valid transfer of funds from one account to another.
     * This mimics the transfer of 200 from ICICI to HDFC in Main.java
     */
    @Test
    public void testSuccessfulTransfer() {
        transferService.transfer(fromAccount, toAccount, 200);
        assertEquals(800, fromAccount.getBalance(),
            "Sender balance after transfer is incorrect.");
        assertEquals(1200, toAccount.getBalance(),
            "Receiver balance after transfer is incorrect.");
    }

    /**
     * Tests a failed transfer due to insufficient balance.
     * Verifies that neither balance changes if funds are inadequate.
     */
    @Test
    public void testTransferWithInsufficientFunds() {
        transferService.transfer(fromAccount, toAccount, 2000);
        assertEquals(1000, fromAccount.getBalance(),
            "Sender balance should remain unchanged.");
        assertEquals(1000, toAccount.getBalance(),
            "Receiver balance should remain unchanged.");
    }

    /**
     * Tests a zero-amount transfer.
     * Ensures no change in balances when zero is transferred.
     */
    @Test
    public void testZeroAmountTransfer() {
        transferService.transfer(fromAccount, toAccount, 0);
        assertEquals(1000, fromAccount.getBalance(),
            "Sender balance should remain unchanged on zero transfer.");
        assertEquals(1000, toAccount.getBalance(),
            "Receiver balance should remain unchanged on zero transfer.");
    }
}
