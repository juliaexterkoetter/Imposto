package imposto.imposto.controller;

import imposto.imposto.model.User;
import imposto.imposto.dto.UserRegistrationDTO;
import imposto.imposto.dto.AuthResponseDTO;
import imposto.imposto.service.UserService;
import imposto.imposto.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationDTO userDTO) {
        // Criação do usuário usando DTO
        User user = new User(userDTO.getUsername(), new BCryptPasswordEncoder().encode(userDTO.getPassword()), userDTO.getRole());
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody User user) {
        User foundUser = userService.findByUsername(user.getUsername());
        if (foundUser != null && new BCryptPasswordEncoder().matches(user.getPassword(), foundUser.getPassword())) {
            String token = jwtTokenProvider.createToken(foundUser.getUsername(), List.of(foundUser.getRole()));
            return new AuthResponseDTO(token);
        }
        throw new RuntimeException("Login failed");
    }
}
