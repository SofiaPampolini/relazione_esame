package org.example.repository;
import org.example.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaccia Repository per l'entità Utente.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria di tipo String.
 */

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String>{
    
}
