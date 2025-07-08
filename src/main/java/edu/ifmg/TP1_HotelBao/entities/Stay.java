package edu.ifmg.TP1_HotelBao.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_stay")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(columnDefinition = "DATE")
    private LocalDate dataEntrada;

    public Stay() {

    }

    public Stay(Long id, Client cliente, Room room, LocalDate dataEntrada) {
        this.id = id;
        this.client = cliente;
        this.room = room;
        this.dataEntrada = dataEntrada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getCliente() {
        return client;
    }

    public void setCliente(Client cliente) {
        this.client = cliente;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stay stay)) return false;
        return Objects.equals(id, stay.id) && Objects.equals(client, stay.client) && Objects.equals(room, stay.room) && Objects.equals(dataEntrada, stay.dataEntrada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, room, dataEntrada);
    }

    @Override
    public String toString() {
        return "Stay{" +
                "id=" + id +
                ", cliente=" + client +
                ", room=" + room +
                ", dataEntrada=" + dataEntrada +
                '}';
    }
}
