package edu.ifmg.TP1_HotelBao.dtos;

public class StayValueDTO {

    private String descricao;
    private Double valor;

    public StayValueDTO() {}

    public StayValueDTO(String descricao, Double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}