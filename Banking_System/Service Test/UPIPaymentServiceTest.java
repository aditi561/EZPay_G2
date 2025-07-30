import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ezpay.services.impl.UPIPaymentServiceImpl;
import com.ezpay.models.UPIRequest;
import com.ezpay.models.BankAccount;
import com.ezpay.repositories.BankAccountRepository;
import com.ezpay.repositories.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UPIPaymentServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private UPIPaymentServiceImpl upiPaymentService;

    private BankAccount senderAccount;
    private BankAccount receiverAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        senderAccount = new BankAccount();
        senderAccount.setId(10L);
        senderAccount.setUpiId("sender@upi");
        senderAccount.setBalance(1500.0);

        receiverAccount = new BankAccount();
        receiverAccount.setId(20L);
        receiverAccount.setUpiId("receiver@upi");
        receiverAccount.setBalance(500.0);
    }

    @Test
    public void testSuccessfulUPIPayment() {
        UPIRequest request = new UPIRequest("sender@upi", "receiver@upi", 500.0);

        when(bankAccountRepository.findByUpiId("sender@upi")).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findByUpiId("receiver@upi")).thenReturn(Optional.of(receiverAccount));

        boolean success = upiPaymentService.sendPayment(request);

        assertTrue(success);
        assertEquals(1000.0, senderAccount.getBalance());
        assertEquals(1000.0, receiverAccount.getBalance());

        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    public void testUPIPaymentInsufficientBalance() {
        senderAccount.setBalance(300.0);
        UPIRequest request = new UPIRequest("sender@upi", "receiver@upi", 500.0);

        when(bankAccountRepository.findByUpiId("sender@upi")).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findByUpiId("receiver@upi")).thenReturn(Optional.of(receiverAccount));

        boolean result = upiPaymentService.sendPayment(request);
        assertFalse(result);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    public void testUPIPaymentInvalidReceiver() {
        UPIRequest request = new UPIRequest("sender@upi", "invalid@upi", 100.0);

        when(bankAccountRepository.findByUpiId("sender@upi")).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findByUpiId("invalid@upi")).thenReturn(Optional.empty());

        boolean result = upiPaymentService.sendPayment(request);
        assertFalse(result);
    }
}

