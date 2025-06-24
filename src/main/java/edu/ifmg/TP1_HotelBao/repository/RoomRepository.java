package edu.ifmg.TP1_HotelBao.repository;

import edu.ifmg.TP1_HotelBao.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
