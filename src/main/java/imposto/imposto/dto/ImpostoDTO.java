package imposto.imposto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImpostoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Double aliquota;
}
