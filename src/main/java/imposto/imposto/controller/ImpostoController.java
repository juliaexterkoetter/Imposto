package imposto.imposto.controller;

import imposto.imposto.dto.ImpostoDTO;
import imposto.imposto.model.Imposto;
import imposto.imposto.service.ImpostoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Impostos", description = "Endpoints para gerenciamento dos tipos de impostos")
@RestController
@RequestMapping("/tipos")
public class ImpostoController {

    @Autowired
    private ImpostoService impostoService;

    @Operation(summary = "Listar todos os impostos", description = "Retorna a lista de todos os impostos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Impostos listados com sucesso")
    })
    @GetMapping
    public List<ImpostoDTO> listarImpostos() {
        List<Imposto> impostos = impostoService.listarTodos();
        return impostos.stream()
                .map(imposto -> new ImpostoDTO(imposto.getId(), imposto.getNome(), imposto.getDescricao(), imposto.getAliquota()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obter imposto por ID", description = "Retorna os detalhes de um imposto específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imposto encontrado"),
            @ApiResponse(responseCode = "404", description = "Imposto não encontrado")
    })
    @GetMapping("/{id}")
    public ImpostoDTO obterImpostoPorId(@PathVariable Long id) {
        Imposto imposto = impostoService.obterImpostoPorId(id);
        return new ImpostoDTO(imposto.getId(), imposto.getNome(), imposto.getDescricao(), imposto.getAliquota());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar novo imposto", description = "Cria um novo imposto. Acesso restrito ao papel ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imposto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImpostoDTO criarImposto(@RequestBody @Valid ImpostoDTO impostoDTO) {
        Imposto imposto = new Imposto();
        imposto.setNome(impostoDTO.getNome());
        imposto.setDescricao(impostoDTO.getDescricao());
        imposto.setAliquota(impostoDTO.getAliquota());
        Imposto criado = impostoService.criarImposto(imposto);
        return new ImpostoDTO(criado.getId(), criado.getNome(), criado.getDescricao(), criado.getAliquota());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar imposto", description = "Atualiza os dados de um imposto existente. Acesso restrito ao papel ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imposto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imposto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ImpostoDTO atualizarImposto(@PathVariable Long id, @RequestBody @Valid ImpostoDTO impostoDTO) {
        Imposto impostoAtualizado = new Imposto();
        impostoAtualizado.setNome(impostoDTO.getNome());
        impostoAtualizado.setDescricao(impostoDTO.getDescricao());
        impostoAtualizado.setAliquota(impostoDTO.getAliquota());
        Imposto atualizado = impostoService.atualizarImposto(id, impostoAtualizado);
        return new ImpostoDTO(atualizado.getId(), atualizado.getNome(), atualizado.getDescricao(), atualizado.getAliquota());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir imposto", description = "Exclui um imposto pelo ID. Acesso restrito ao papel ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imposto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imposto não encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirImposto(@PathVariable Long id) {
        impostoService.excluirImposto(id);
    }
}
