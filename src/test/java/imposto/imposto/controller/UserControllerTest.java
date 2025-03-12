package imposto.imposto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import imposto.imposto.dto.AuthResponseDTO;
import imposto.imposto.dto.UserRegistrationDTO;
import imposto.imposto.model.User;
import imposto.imposto.security.JwtTokenProvider;
import imposto.imposto.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Usado para converter objetos em JSON
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testRegisterUser() throws Exception {
        // Cria um DTO para o registro
        UserRegistrationDTO userDTO = new UserRegistrationDTO("usuario123", "senhaSegura", "USER");

        // Cria o objeto User que será retornado pelo serviço
        User user = new User("usuario123", "encodedSenha", "USER");

        // Configura os mocks:
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedSenha");
        when(userService.registerUser(any(User.class))).thenReturn(user);

        // Executa a requisição POST para o endpoint /user/register
        // Como o controller está anotado com @ResponseStatus(HttpStatus.CREATED), esperamos 201
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testLogin() throws Exception {
        // Cria um objeto User para simular o login
        User user = new User("usuario123", "encodedSenha", "USER");

        // Configura os mocks:
        when(userService.findByUsername("usuario123")).thenReturn(user);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenProvider.createToken(anyString(), any(List.class))).thenReturn("token");

        // Executa a requisição POST para o endpoint /user/login
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }
}
