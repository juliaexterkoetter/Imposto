package imposto.imposto.controller;

import imposto.imposto.model.Imposto;
import imposto.imposto.dto.ImpostoDTO;
import imposto.imposto.service.ImpostoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tipos")
public class ImpostoController {

    @Autowired
    private ImpostoService impostoService;

    @GetMapping
    public List<ImpostoDTO> listarImpostos() {
        List<Imposto> impostos = impostoService.listarTodos();
        return impostos.stream()
                .map(imposto -> new ImpostoDTO(imposto.getId(), imposto.getNome(), imposto.getValor()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ImpostoDTO criarImposto(@RequestBody ImpostoDTO impostoDTO) {
        Imposto imposto = new Imposto(impostoDTO.getNome(), impostoDTO.getValor());
        Imposto criado = impostoService.criarImposto(imposto);
        return new ImpostoDTO(criado.getId(), criado.getNome(), criado.getValor());
    }

    @DeleteMapping("/{id}")
    public void excluirImposto(@PathVariable Long id) {
        impostoService.excluirImposto(id);
    }
}
