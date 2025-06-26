package edu.ifmg.TP1_HotelBao.dtos;

import edu.ifmg.TP1_HotelBao.entities.Stay;

import java.time.Instant;

public class StayDTO {

    private Long id;
    private Long clientId;
    private Long roomId;
    private Instant dataEntrada;

    public StayDTO() {
    }

    public StayDTO(Long id, Long clientId, Long roomId, Instant dataEntrada) {
        this.id = id;
        this.clientId = clientId;
        this.roomId = roomId;
        this.dataEntrada = dataEntrada;
    }

    public StayDTO(Stay entity) {
        this.id = entity.getId();
        this.clientId = entity.getCliente().getId();
        this.roomId = entity.getRoom().getId();
        this.dataEntrada = entity.getDataEntrada();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Instant getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Instant dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    @Override
    public String toString() {
        return "StayDTO{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", roomId=" + roomId +
                ", dataEntrada=" + dataEntrada +
                '}';
    }
}
