package imposto.imposto.controller;

import imposto.imposto.model.User;
import imposto.imposto.service.UserService;
import imposto.imposto.security.JwtTokenProvider;
import imposto.imposto.dto.AuthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setUsername("usuario123");
        user.setPassword("senhaSegura");
        user.setRole("USER");

        when(userService.registerUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = restTemplate.postForEntity("/user/register", user, User.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testLogin() {
        User user = new User();
        user.setUsername("usuario123");
        user.setPassword("senhaSegura");

        when(userService.findByUsername("usuario123")).thenReturn(user);
        when(jwtTokenProvider.createToken(anyString(), anyList())).thenReturn("token");

        ResponseEntity<AuthResponseDTO> response = restTemplate.postForEntity("/user/login", user, AuthResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
