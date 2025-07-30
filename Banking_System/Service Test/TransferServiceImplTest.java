import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ezpay.services.impl.TransferServiceImpl;
import com.ezpay.models.TransferRequest;
import com.ezpay.models.BankAccount;
import com.ezpay.repositories.BankAccountRepository;
import com.ezpay.repositories.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

/**
 * Unit tests for TransferServiceImpl.
 * This class verifies the logic for transferring funds between bank accounts.
 */
public class TransferServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private BankAccount senderAccount;
    private BankAccount receiverAccount;

    /**
     * Initializes common test data before each test method.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock sender account
        senderAccount = new BankAccount();
        senderAccount.setId(1L);
        senderAccount.setBalance(5000.0);

        // Create mock receiver account
        receiverAccount = new BankAccount();
        receiverAccount.setId(2L);
        receiverAccount.setBalance(2000.0);
    }

    /**
     * Test case: Successful fund transfer between valid accounts.
     */
    @Test
    public void testTransferSuccess() {
        TransferRequest request = new TransferRequest(1L, 2L, 1000.0);

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        boolean result = transferService.transfer(request);

        assertTrue(result);
        assertEquals(4000.0, senderAccount.getBalance());
        assertEquals(3000.0, receiverAccount.getBalance());

        // Verify transaction was saved
        verify(transactionRepository, times(1)).save(any());
    }

    /**
     * Test case: Transfer fails due to insufficient balance.
     */
    @Test
    public void testTransferInsufficientBalance() {
        senderAccount.setBalance(500.0); // Not enough to transfer 1000
        TransferRequest request = new TransferRequest(1L, 2L, 1000.0);

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        boolean result = transferService.transfer(request);

        assertFalse(result);
        verify(transactionRepository, never()).save(any());
    }
}
