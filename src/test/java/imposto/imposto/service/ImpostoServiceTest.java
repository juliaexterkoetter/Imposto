package imposto.imposto.service;

import imposto.imposto.model.Imposto;
import imposto.imposto.repository.ImpostoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ImpostoServiceTest {

    @InjectMocks
    private ImpostoService impostoService;

    @Mock
    private ImpostoRepository impostoRepository;

    private Imposto imposto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        imposto = new Imposto();
        imposto.setId(1L);
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre Circulação de Mercadorias e Serviços");
        imposto.setAliquota(18.0);
    }

    @Test
    public void testCriarImposto() {
        when(impostoRepository.save(any(Imposto.class))).thenReturn(imposto);

        Imposto result = impostoService.criarImposto(imposto);

        assertNotNull(result);
        assertEquals("ICMS", result.getNome());
        verify(impostoRepository, times(1)).save(imposto);
    }

    @Test
    public void testListarTodosImpostos() {
        when(impostoRepository.findAll()).thenReturn(List.of(imposto));

        List<Imposto> impostos = impostoService.listarTodos();

        assertFalse(impostos.isEmpty());
        assertEquals(1, impostos.size());
    }

    @Test
    public void testExcluirImposto() {
        doNothing().when(impostoRepository).deleteById(1L);

        impostoService.excluirImposto(1L);

        verify(impostoRepository, times(1)).deleteById(1L);
    }
}
