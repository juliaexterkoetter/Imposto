package imposto.imposto.controller;

import imposto.imposto.dto.CalculoImpostoDTO;
import imposto.imposto.dto.CalculoImpostoRequestDTO;
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

@Tag(name = "Cálculo de Impostos", description = "Endpoint para cálculo do imposto com base no tipo e valor base. Acesso restrito a ADMIN")
@RestController
@RequestMapping("/calculo")
public class CalculoController {

    @Autowired
    private ImpostoService impostoService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Calcular imposto", description = "Calcula o valor do imposto com base no tipo de imposto e no valor base informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CalculoImpostoDTO calcularImposto(@RequestBody @Valid CalculoImpostoRequestDTO request) {
        Imposto imposto = impostoService.obterImpostoPorId(request.getTipoImpostoId());
        double valorImposto = impostoService.calcularImposto(request.getTipoImpostoId(), request.getValorBase());
        return new CalculoImpostoDTO(
                imposto.getNome(),
                request.getValorBase(),
                imposto.getAliquota(),
                valorImposto
        );
    }
}
