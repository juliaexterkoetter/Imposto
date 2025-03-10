package imposto.imposto.service;

import imposto.imposto.model.User;
import imposto.imposto.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("usuario123");
        user.setPassword("senhaSegura");
        user.setRole("USER");
    }

    @Test
    public void testRegisterUser() {
        // Configura o comportamento do encoder
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedSenha");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("usuario123", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindByUsername() {
        when(userRepository.findByUsername("usuario123")).thenReturn(user);

        User result = userService.findByUsername("usuario123");

        assertNotNull(result);
        assertEquals("usuario123", result.getUsername());
    }
}
