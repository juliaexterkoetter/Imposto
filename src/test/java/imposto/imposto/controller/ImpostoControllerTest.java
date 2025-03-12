package imposto.imposto.controller;

import imposto.imposto.dto.ImpostoDTO;
import imposto.imposto.model.Imposto;
import imposto.imposto.service.ImpostoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImpostoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ImpostoService impostoService;

    @Test
    public void testCriarImposto() {
        // Configura um imposto de exemplo
        Imposto imposto = new Imposto();
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre Circulação de Mercadorias");
        imposto.setAliquota(18.0);

        // Simula a criação do imposto
        when(impostoService.criarImposto(any(Imposto.class))).thenReturn(imposto);

        // O controller retorna um ImpostoDTO
        ResponseEntity<ImpostoDTO> response = restTemplate.postForEntity("/tipos", imposto, ImpostoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("ICMS");
    }

    @Test
    public void testListarImpostos() {
        Imposto imposto = new Imposto();
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre Circulação de Mercadorias");
        imposto.setAliquota(18.0);

        when(impostoService.listarTodos()).thenReturn(List.of(imposto));

        // Para endpoints que retornam uma lista de DTOs, podemos esperar um array
        ResponseEntity<ImpostoDTO[]> response = restTemplate.getForEntity("/tipos", ImpostoDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ImpostoDTO[] dtos = response.getBody();
        assertThat(dtos).isNotNull();
        assertThat(dtos.length).isGreaterThan(0);
    }
}
