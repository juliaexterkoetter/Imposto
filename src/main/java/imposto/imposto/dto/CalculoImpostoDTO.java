package imposto.imposto.dto;

public class CalculoImpostoDTO {
    private String tipoImposto;
    private Double valorBase;
    private Double aliquota;
    private Double valorImposto;

    // Construtores, Getters e Setters
    public CalculoImpostoDTO(String tipoImposto, Double valorBase, Double aliquota, Double valorImposto) {
        this.tipoImposto = tipoImposto;
        this.valorBase = valorBase;
        this.aliquota = aliquota;
        this.valorImposto = valorImposto;
    }

    public String getTipoImposto() {
        return tipoImposto;
    }

    public void setTipoImposto(String tipoImposto) {
        this.tipoImposto = tipoImposto;
    }

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }

    public Double getAliquota() {
        return aliquota;
    }

    public void setAliquota(Double aliquota) {
        this.aliquota = aliquota;
    }

    public Double getValorImposto() {
        return valorImposto;
    }

    public void setValorImposto(Double valorImposto) {
        this.valorImposto = valorImposto;
    }
}
