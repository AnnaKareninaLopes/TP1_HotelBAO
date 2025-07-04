package edu.ifmg.TP1_HotelBao.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private Double valor;
    private String imagemUrl;

    @OneToMany(mappedBy = "room")
    private List<Stay> stays = new ArrayList<>();

    public Room() {
    }

    public Room(Long id, String descricao, Double valor, String imagemUrl) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.imagemUrl = imagemUrl;
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

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", imagemUrl='" + imagemUrl + '\'' +
                '}';
    }

}
