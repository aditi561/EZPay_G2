import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ezpay.services.impl.UserServiceImpl;
import com.ezpay.models.User;
import com.ezpay.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("john_doe");
        testUser.setEmail("john@example.com");
    }

    @Test
    public void testRegisterUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        User registered = userService.registerUser(testUser);
        assertNotNull(registered);
        assertEquals("john_doe", registered.getUsername());
    }

    @Test
    public void testFindUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        User user = userService.findUserById(1L);
        assertNotNull(user);
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    public void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        List<User> users = userService.findAllUsers();
        assertEquals(1, users.size());
        assertEquals("john_doe", users.get(0).getUsername());
    }
}
