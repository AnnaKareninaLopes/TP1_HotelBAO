package edu.ifmg.TP1_HotelBao.repository;

import edu.ifmg.TP1_HotelBao.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

}
