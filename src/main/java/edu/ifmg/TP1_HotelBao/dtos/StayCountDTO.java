package edu.ifmg.TP1_HotelBao.dtos;

public class StayCountDTO {

    private Long clientId;
    private Double quantidade;

    public StayCountDTO() {}

    public StayCountDTO(Long clientId, Double quantidade) {
        this.clientId = clientId;
        this.quantidade = quantidade;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}