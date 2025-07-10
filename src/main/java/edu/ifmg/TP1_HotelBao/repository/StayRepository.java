package edu.ifmg.TP1_HotelBao.repository;

import edu.ifmg.TP1_HotelBao.dtos.InvoiceStayDTO;
import edu.ifmg.TP1_HotelBao.entities.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StayRepository extends JpaRepository<Stay,Long> {

    @Query(nativeQuery = true,
            value = """
                SELECT COUNT(*) > 0 
                FROM tb_client 
                WHERE id = :clientId
            """)
    Boolean existsClientById(@Param("clientId") Long clientId);

    @Query(nativeQuery = true,
            value = """
                SELECT COUNT(*) > 0 
                FROM tb_room 
                WHERE id = :roomId
            """)
    Boolean existsRoomById(@Param("roomId") Long roomId);

    @Query(nativeQuery = true,
            value = """
                SELECT COUNT(*) > 0 
                FROM tb_stay 
                WHERE room_id = :roomId AND data_entrada = :dataEntrada
            """)
    Long existsRoomOccupied(@Param("roomId") Long roomId, @Param("dataEntrada") LocalDate dataEntrada);

    @Query("SELECT new edu.ifmg.TP1_HotelBao.dtos.InvoiceStayDTO(" +
            "s.id, s.dataEntrada, r.descricao, r.valor) " +
            "FROM Stay s JOIN s.room r " +
            "WHERE s.client.id = :clientId")
    List<InvoiceStayDTO> buscarEstadiasDoCliente(@Param("clientId") Long clientId);

    @Query("SELECT SUM(r.valor) FROM Stay s JOIN s.room r WHERE s.client.id = :clientId")
    Double calcularTotalPorCliente(@Param("clientId") Long clientId);

    @Query(value = """
    SELECT s.* FROM tb_stay s
    INNER JOIN tb_room r ON s.room_id = r.id
    WHERE s.client_id = :clientId
    ORDER BY r.valor DESC
    LIMIT 1
    """, nativeQuery = true)
    Optional<Stay> findMaxStayByClientId(@Param("clientId") Long clientId);

    @Query(value = """
    SELECT s.* FROM tb_stay s
    INNER JOIN tb_room r ON s.room_id = r.id
    WHERE s.client_id = :clientId
    ORDER BY r.valor ASC
    LIMIT 1
    """, nativeQuery = true)
    Optional<Stay> findMinStayByClientId(@Param("clientId") Long clientId);

}
