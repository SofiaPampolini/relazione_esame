package org.example.repository;

import java.util.List;

import org.example.model.Notifica;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia Repository per l'entità Notifica.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria di tipo Long.
 */

public interface NotificaRepository extends JpaRepository<Notifica, Long>{
    @Query(value = "SELECT * FROM notifica WHERE Username = :username AND Letta = 0", nativeQuery = true)
    List<Notifica> findByUsernameAndLettaFalse(@Param("username") String username);

    /**
     * Query derivata per verificare la presenza di una notifica con uno specifico testo per un determinato utente.
     * Utilizzata per evitare di inviare messaggi duplicati allo stesso utente.
    */
    Boolean existsByUsernameAndMessaggio(String username, String messaggio);
}
