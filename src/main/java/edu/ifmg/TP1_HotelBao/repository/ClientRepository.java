package edu.ifmg.TP1_HotelBao.repository;

import edu.ifmg.TP1_HotelBao.entities.Client;
import edu.ifmg.TP1_HotelBao.projections.UserDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByLogin(String login);
    Client findByEmail(String email);
    Client findByLoginAndPassword(String login, String password);
    Client findByEmailAndPassword(String email, String password);

    boolean existsById(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tb_client_role", nativeQuery = true)
    void deleteAllClientRoles();

    void deleteAll();

    @Query(nativeQuery = true, value = """
        SELECT c.login AS username,
               c.password AS password,
               r.id AS roleId,
               r.authority
        FROM tb_client c
        INNER JOIN tb_client_role cr ON c.id = cr.client_id
        INNER JOIN tb_role r ON r.id = cr.role_id
        WHERE c.login = :username
    """)
    List<UserDetailsProjection> findUserAndRoleByLogin(@Param("username") String username);

    @Query(
            nativeQuery = true,
            value = """
            SELECT c.id, c.email, c.login, c.username, c.password, c.celular, c.endereco
            FROM tb_client c
            INNER JOIN tb_client_role cr ON c.id = cr.client_id
            INNER JOIN tb_role r ON r.id = cr.role_id
            WHERE r.authority = 'ROLE_CLIENT'
        """,
            countQuery = """
            SELECT COUNT(*)
            FROM client c
            INNER JOIN tb_client_role cr ON c.id = cr.client_id
            INNER JOIN tb_role r ON r.id = cr.role_id
            WHERE r.authority = 'ROLE_CLIENT'
        """
    )
    Page<Client> findAllClients(Pageable pageable);
}

