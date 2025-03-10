package imposto.imposto.security;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Test
    public void testCreateToken() {
        String token = jwtTokenProvider.createToken("usuario123", List.of("USER"));
        assertNotNull(token);
    }

    @Test
    public void testValidateToken() {
        String token = jwtTokenProvider.createToken("usuario123", List.of("USER"));
        assertTrue(jwtTokenProvider.validateToken(token));
    }
}
