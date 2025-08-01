import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for TransferServiceImpl
 */
public class TransferServiceImplTest {

    private TransferServiceImpl transferService;
    private BankAccountServiceImpl fromAccount;
    private BankAccountServiceImpl toAccount;

    @BeforeEach
    public void setup() {
        transferService = new TransferServiceImpl();
        fromAccount = new BankAccountServiceImpl();
        toAccount = new BankAccountServiceImpl();
        fromAccount.deposit(1000);
    }

    /**
     * Test for successful transfer
     * Verifies that amount is correctly transferred between accounts.
     */
    @Test
    public void testTransferSuccess() {
        transferService.transfer(fromAccount, toAccount, 300);
        assertEquals(700, fromAccount.getBalance());
        assertEquals(300, toAccount.getBalance());
    }

    /**
     * Test for transfer with insufficient funds
     * Verifies that transfer doesn't happen when balance is insufficient.
     */
    @Test
    public void testTransferInsufficientFunds() {
        transferService.transfer(fromAccount, toAccount, 1500); // more than available
        assertEquals(1000, fromAccount.getBalance());
        assertEquals(0, toAccount.getBalance());
    }

    /**
     * Test for transfer with zero amount
     * Verifies that no change occurs when transfer amount is zero.
     */
    @Test
    public void testTransferZeroAmount() {
        transferService.transfer(fromAccount, toAccount, 0);
        assertEquals(1000, fromAccount.getBalance());
        assertEquals(0, toAccount.getBalance());
    }
}
