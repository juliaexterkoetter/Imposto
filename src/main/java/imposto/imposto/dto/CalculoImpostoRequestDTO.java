package imposto.imposto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculoImpostoRequestDTO {
    @NotNull(message = "ID do tipo de imposto é obrigatório")
    private Long tipoImpostoId;

    @NotNull(message = "Valor base é obrigatório")
    @PositiveOrZero(message = "Valor base deve ser zero ou positivo")
    private Double valorBase;
}
