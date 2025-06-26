package edu.ifmg.TP1_HotelBao.dtos;

import edu.ifmg.TP1_HotelBao.entities.Room;
import edu.ifmg.TP1_HotelBao.entities.Stay;
import edu.ifmg.TP1_HotelBao.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO {

    private Long id;
    private String descricao;
    private Double valor;
    private String imagemUrl;
    private List<StayDTO> stays = new ArrayList<>();

    public RoomDTO() {
    }

    public RoomDTO(Long id, String descricao, Double valor, String imagemUrl) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.imagemUrl = imagemUrl;
    }

    public RoomDTO(Room entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        this.valor = entity.getValor();
        this.imagemUrl = entity.getImagemUrl();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public List<StayDTO> getStays() {
        return stays;
    }

    public void setStays(List<StayDTO> stays) {
        this.stays = stays;
    }
}
