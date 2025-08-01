import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for BankAccountServiceImpl
 */
public class BankAccountServiceImplTest {

    private BankAccountServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new BankAccountServiceImpl();
    }

    /**
     * Test for deposit method
     * Verifies that depositing a valid amount updates the balance correctly.
     */
    @Test
    public void testDeposit() {
        double initialBalance = service.getBalance();
        service.deposit(500);
        assertEquals(initialBalance + 500, service.getBalance());
    }

    /**
     * Test for withdraw method
     * Verifies that withdrawing a valid amount updates the balance correctly.
     */
    @Test
    public void testWithdraw() {
        service.deposit(1000); // Ensure sufficient balance
        double initialBalance = service.getBalance();
        service.withdraw(300);
        assertEquals(initialBalance - 300, service.getBalance());
    }

    /**
     * Test for withdraw method with insufficient funds
     * Verifies that balance remains unchanged when withdrawal exceeds current balance.
     */
    @Test
    public void testWithdrawInsufficientFunds() {
        double initialBalance = service.getBalance();
        service.withdraw(initialBalance + 1000); // More than balance
        assertEquals(initialBalance, service.getBalance());
    }
}
