package org.example.repository;

import java.util.List;
import java.util.Optional;

import org.example.dto.SpettacoloProjection;
import org.example.model.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interfaccia Repository per l'entità Spettacolo.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria di tipo String. 
 */

public interface SpettacoloRepository extends JpaRepository<Spettacolo, String> {
    // Recupero di tutti gli spettacoli presenti
    @Query(value = """
        SELECT s.Id_Spettacolo AS id,
               s.Nome AS nome,
               s.Genere AS genere,
               r.Data AS data,
               r.Ora AS ora,
               t.Codice_Teatro AS codiceTeatro,
               t.Nome AS nomeTeatro,
               t.Citta AS cittaTeatro
        FROM spettacolo s
        JOIN rappresenta r ON s.Id_Spettacolo = r.Id_Spettacolo
        JOIN teatro t ON r.Codice_Teatro = t.Codice_Teatro
        WHERE r.Data >= CURDATE() AND r.Stato != 'Annullato'
        """, nativeQuery = true)
    List<SpettacoloProjection> findAllSpettacoliConTeatro();

    // Recupero delle informazioni di uno spettacolo tramite ID
    @Query(value = """
        SELECT s.Id_Spettacolo AS id,
               s.Nome AS nome,
               s.Genere AS genere,
               r.Data AS data,
               r.Ora AS ora,
               t.Codice_Teatro AS codiceTeatro,
               t.Nome AS nomeTeatro,
               t.Citta AS cittaTeatro
        FROM spettacolo s
        JOIN rappresenta r ON s.Id_Spettacolo = r.Id_Spettacolo
        JOIN teatro t ON r.Codice_Teatro = t.Codice_Teatro
        WHERE s.Id_Spettacolo = :id
        LIMIT 1
        """, nativeQuery = true)
    Optional<SpettacoloProjection> findSpettacoloById(@Param("id") String id);

    // Ricerca e filtraggio dinamico degli spettacoli lato utente
    @Query(value = """
            SELECT s.Id_Spettacolo AS id,
                   s.Nome AS nome,
                   s.Genere AS genere,
                   r.Data AS data,
                   r.Ora AS ora,
                   t.Codice_Teatro AS codiceTeatro,
                   t.Nome AS nomeTeatro,
                   t.Citta AS cittaTeatro
            FROM rappresenta r
            JOIN spettacolo s ON r.Id_Spettacolo = s.Id_Spettacolo
            JOIN teatro t ON r.Codice_Teatro = t.Codice_Teatro
            WHERE r.Data >= CURDATE() AND r.stato <> 'Annullato'
                AND (:query IS NULL OR :query = '' OR LOWER(s.Nome) LIKE LOWER(CONCAT('%', :query, '%'))
                                                   OR LOWER(t.Nome) LIKE LOWER(CONCAT('%', :query, '%'))
                                                   OR LOWER(t.Citta) LIKE LOWER(CONCAT('%', :query, '%')))
                AND (:genere IS NULL OR :genere = '' OR LOWER(s.Genere) LIKE LOWER(CONCAT('%', :genere, '%')))
                AND (:data IS NULL OR :data = '' OR r.Data = :data)
                AND (:citta IS NULL OR :citta = '' OR LOWER(t.Citta) LIKE LOWER(CONCAT('%', :citta, '%')))
                
            """, nativeQuery = true)
    List<SpettacoloProjection> filtraSpettacoli(
        @Param("query") String query,
        @Param("genere") String genere,
        @Param("data") String data,
        @Param("citta") String citta);

    // Calcolo dell'identificativo massimo attualmente registrato per gli spettacoli
    @Query(value = """
            SELECT MAX(s.Id_Spettacolo) FROM spettacolo s
            """, nativeQuery = true)
    String findMaxIdSpettacolo();

    Optional<Spettacolo> findByNomeIgnoreCase(String nome);
}