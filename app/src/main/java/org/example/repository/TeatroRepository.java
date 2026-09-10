package org.example.repository;

import java.util.List;

import org.example.model.Teatro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interfaccia Repository per l'entità Teatro.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria di tipo String.
 */

public interface TeatroRepository extends JpaRepository<Teatro, String>{
    // Ricerca dell'codice teatro massimo attualmente registrato
    @Query(value = """
            SELECT MAX(t.Codice_Teatro) FROM teatro t
            """, nativeQuery = true)
    String findMaxCodiceTeatro();

    // Recupero dell'elenco completo di tutti i teatri ordinati per città e nome
    @Query(value = """
            SELECT *
            FROM teatro
            ORDER BY Citta ASC, Nome ASC
            """, nativeQuery = true)
    List<Teatro> findAllByOrderByNomeAscOrderByCittaAsc();

    boolean existsByNomeIgnoreCaseAndCittaIgnoreCase(String nome, String citta);
}
