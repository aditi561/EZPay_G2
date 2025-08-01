import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for BankAccountServiceImpl.
 * Simulates deposit and withdrawal operations as seen in the Main class.
 */
public class BankAccountServiceImplTest {

    private BankAccountServiceImpl bankAccount;

    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccountServiceImpl();
    }

    /**
     * Test the deposit method.
     * This test simulates depositing 1000 units as done in Main.java
     * and verifies that the balance is updated accordingly.
     */
    @Test
    public void testDeposit() {
        bankAccount.deposit(1000);
        assertEquals(1000, bankAccount.getBalance(),
            "Deposit failed to update balance correctly.");
    }

    /**
     * Test the withdraw method.
     * Deposits an amount, withdraws a smaller amount, and
     * checks if balance is reduced correctly.
     */
    @Test
    public void testWithdraw() {
        bankAccount.deposit(1000);
        bankAccount.withdraw(200);
        assertEquals(800, bankAccount.getBalance(),
            "Withdraw did not deduct amount properly.");
    }

    /**
     * Test withdraw with insufficient balance.
     * Ensures that withdrawing more than available balance
     * doesn't change the balance (as per implementation).
     */
    @Test
    public void testWithdrawInsufficientFunds() {
        bankAccount.deposit(500);
        bankAccount.withdraw(1000); // Overdraw
        assertEquals(500, bankAccount.getBalance(),
            "Balance should remain unchanged if withdrawal exceeds funds.");
    }
}
