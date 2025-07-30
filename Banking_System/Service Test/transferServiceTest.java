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

public class TransferServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private BankAccount senderAccount;
    private BankAccount receiverAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        senderAccount = new BankAccount();
        senderAccount.setId(1L);
        senderAccount.setBalance(5000.0);

        receiverAccount = new BankAccount();
        receiverAccount.setId(2L);
        receiverAccount.setBalance(2000.0);
    }

    @Test
    public void testTransferSuccess() {
        TransferRequest request = new TransferRequest(1L, 2L, 1000.0);

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        boolean result = transferService.transfer(request);

        assertTrue(result);
        assertEquals(4000.0, senderAccount.getBalance());
        assertEquals(3000.0, receiverAccount.getBalance());

        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    public void testTransferInsufficientBalance() {
        senderAccount.setBalance(500.0);
        TransferRequest request = new TransferRequest(1L, 2L, 1000.0);

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        boolean result = transferService.transfer(request);
        assertFalse(result);
        verify(transactionRepository, never()).save(any());
    }
}

