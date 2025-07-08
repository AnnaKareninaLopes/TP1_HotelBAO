package edu.ifmg.TP1_HotelBao.repository;

import edu.ifmg.TP1_HotelBao.entities.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StayRepository extends JpaRepository<Stay,Long> {

    @Query(nativeQuery = true,
            value = """
                SELECT COUNT(*) > 0 
                FROM tb_client 
                WHERE id = :clientId
            """)
    boolean existsClientById(@Param("clientId") Long clientId);

    @Query(nativeQuery = true,
            value = """
                SELECT COUNT(*) > 0 
                FROM tb_room 
                WHERE id = :roomId
            """)
    boolean existsRoomById(@Param("roomId") Long roomId);

    @Query(nativeQuery = true,
            value = """
                SELECT COUNT(*) > 0 
                FROM tb_stay 
                WHERE room_id = :roomId AND data_entrada = :dataEntrada
            """)
    boolean existsRoomOccupied(@Param("roomId") Long roomId, @Param("dataEntrada") LocalDate dataEntrada);

}
