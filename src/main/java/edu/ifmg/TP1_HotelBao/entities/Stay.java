package edu.ifmg.TP1_HotelBao.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_stay")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Client cliente;

    //private Room room;

    private LocalDate dataEntrada;
    private LocalDate dataSaida; //reserva é um dia, será que coloca saída?



}
