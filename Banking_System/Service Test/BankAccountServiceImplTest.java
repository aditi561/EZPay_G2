import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ezpay.services.impl.BankAccountServiceImpl;
import com.ezpay.models.BankAccount;
import com.ezpay.repositories.BankAccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

/**
 * Unit tests for BankAccountServiceImpl.
 * This class tests various functionalities related to bank account operations.
 */
public class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount testAccount;

    /**
     * Initialize test data and mock dependencies.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Creating a sample BankAccount object
        testAccount = new BankAccount();
        testAccount.setId(1L);
        testAccount.setUserId(101L);
        testAccount.setAccountNumber("123456789");
        testAccount.setBalance(1000.0);
    }

    /**
     * Test fetching a bank account by its ID.
     */
    @Test
    public void testGetBankAccountById() {
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        BankAccount account = bankAccountService.getBankAccountById(1L);

        assertNotNull(account);
        assertEquals("123456789", account.getAccountNumber());
    }

    /**
     * Test saving a new bank account.
     */
    @Test
    public void testAddBankAccount() {
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(testAccount);

        BankAccount saved = bankAccountService.addBankAccount(testAccount);

        assertNotNull(saved);
        assertEquals(testAccount.getUserId(), saved.getUserId());
    }

    /**
     * Test retrieving all bank accounts for a specific user.
     */
    @Test
    public void testGetAllBankAccountsForUser() {
        when(bankAccountRepository.findByUserId(101L)).thenReturn(List.of(testAccount));

        List<BankAccount> accounts = bankAccountService.getAllBankAccountsForUser(101L);

        assertEquals(1, accounts.size());
    }
}
