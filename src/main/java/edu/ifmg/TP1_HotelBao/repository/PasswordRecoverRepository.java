package edu.ifmg.TP1_HotelBao.repository;

import edu.ifmg.TP1_HotelBao.entities.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long> {

    @Query("SELECT obj FROM PasswordRecover obj WHERE (obj.token = :email) " +
           "AND (obj.expiration > :now)")
    public List<PasswordRecover> searchValidToken(String token, Instant now);

}
