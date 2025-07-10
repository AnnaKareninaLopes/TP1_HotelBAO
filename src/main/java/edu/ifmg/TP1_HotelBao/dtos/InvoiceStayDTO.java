package edu.ifmg.TP1_HotelBao.dtos;

import java.time.LocalDate;

public class InvoiceStayDTO {

    private Long stayId;
    private LocalDate dataEntrada;
    private String descricaoQuarto;
    private Double valorQuarto;

    public InvoiceStayDTO() {}

    public InvoiceStayDTO(Long stayId, LocalDate dataEntrada, String descricaoQuarto, Double valorQuarto) {
        this.stayId = stayId;
        this.dataEntrada = dataEntrada;
        this.descricaoQuarto = descricaoQuarto;
        this.valorQuarto = valorQuarto;
    }

    public Long getStayId() {
        return stayId;
    }

    public void setStayId(Long stayId) {
        this.stayId = stayId;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getDescricaoQuarto() {
        return descricaoQuarto;
    }

    public void setDescricaoQuarto(String descricaoQuarto) {
        this.descricaoQuarto = descricaoQuarto;
    }

    public Double getValorQuarto() {
        return valorQuarto;
    }

    public void setValorQuarto(Double valorQuarto) {
        this.valorQuarto = valorQuarto;
    }
}
