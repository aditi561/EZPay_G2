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

public class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount testAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        testAccount = new BankAccount();
        testAccount.setId(1L);
        testAccount.setUserId(101L);
        testAccount.setAccountNumber("123456789");
        testAccount.setBalance(1000.0);
    }

    @Test
    public void testGetBankAccountById() {
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        BankAccount account = bankAccountService.getBankAccountById(1L);
        assertNotNull(account);
        assertEquals("123456789", account.getAccountNumber());
    }

    @Test
    public void testAddBankAccount() {
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(testAccount);
        BankAccount saved = bankAccountService.addBankAccount(testAccount);
        assertNotNull(saved);
        assertEquals(testAccount.getUserId(), saved.getUserId());
    }

    @Test
    public void testGetAllBankAccountsForUser() {
        List<BankAccount> mockList = List.of(testAccount);
        when(bankAccountRepository.findByUserId(101L)).thenReturn(mockList);
        List<BankAccount> accounts = bankAccountService.getAllBankAccountsForUser(101L);
        assertEquals(1, accounts.size());
    }
}

