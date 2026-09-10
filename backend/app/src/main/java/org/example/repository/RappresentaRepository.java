package org.example.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.example.dto.SpettacoloAdminProjection;
import org.example.model.Rappresenta;
import org.example.model.RappresentaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interfaccia Repository per l'entità Rappresenta.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria composta RappresentaId.
 */

public interface RappresentaRepository extends JpaRepository<Rappresenta, RappresentaId>{
    // Elenco di tutti gli spettacoli in programma con clacolo dinamico dei posti ancora liberi
    @Query(value = """
            SELECT s.Id_Spettacolo AS idSpettacolo, 
                   s.Nome AS nome, 
                   t.Codice_Teatro AS codiceTeatro,
                   t.Nome AS nomeTeatro, 
                   t.Citta AS cittaTeatro, 
                   r.Data AS data,
                   r.Ora AS ora, 
                   (t.Capacita - COUNT(sp.Numero_Posto)) AS postiLiberi
            FROM rappresenta r
            JOIN spettacolo s ON r.Id_Spettacolo = s.Id_Spettacolo
            JOIN teatro t ON r.Codice_Teatro = t.Codice_Teatro
            LEFT JOIN prenotazione p ON p.Codice_Teatro = r.Codice_Teatro
                                    AND p.Id_Spettacolo = r.Id_Spettacolo
                                    AND p.Data_Spettacolo = r.Data
                                    AND p.Stato <> 'Cancellata'
            LEFT JOIN specifica_posto sp ON sp.Codice_Prenotazione = p.Codice_Prenotazione
                                        AND sp.Codice_Teatro = p.Codice_Teatro
            GROUP BY s.Id_Spettacolo, s.Nome, t.Codice_Teatro, t.Nome, t.Citta, r.Data, r.Ora, t.Capacita
            """, nativeQuery = true)
    List<SpettacoloAdminProjection> findAllSpettacoliConPostiLiberi();

    // Ricerca e filtraggio multi-criterio degli spettacoli lato amministratore o utente
    @Query(value = """
        SELECT s.Id_Spettacolo AS idSpettacolo, 
                s.Nome AS nome, 
                t.Codice_Teatro AS codiceTeatro,
                t.Nome AS nomeTeatro, 
                t.Citta AS cittaTeatro, 
                r.Data AS data,
                r.Ora AS ora, 
                (t.Capacita - COUNT(sp.Numero_Posto)) AS postiLiberi
        FROM rappresenta r
        JOIN spettacolo s ON r.Id_Spettacolo = s.Id_Spettacolo
        JOIN teatro t ON r.Codice_Teatro = t.Codice_Teatro
        LEFT JOIN prenotazione p ON p.Codice_Teatro = r.Codice_Teatro
                                AND p.Id_Spettacolo = r.Id_Spettacolo
                                AND p.Data_Spettacolo = r.Data
                                AND p.Stato <> 'Cancellata'
        LEFT JOIN specifica_posto sp ON sp.Codice_Prenotazione = p.Codice_Prenotazione
                                    AND sp.Codice_Teatro = p.Codice_Teatro
        WHERE r.Stato <> 'Annullato'
            AND (:query IS NULL OR :query = '' OR LOWER(s.Nome) LIKE LOWER(CONCAT('%', :query, '%'))
                                                OR LOWER(t.Nome) LIKE LOWER(CONCAT('%', :query, '%'))
                                                OR LOWER(t.Citta) LIKE LOWER(CONCAT('%', :query, '%')))
            AND (:genere IS NULL OR :genere = '' OR LOWER(s.Genere) LIKE LOWER(CONCAT('%', :genere, '%')))
            AND (:data IS NULL OR :data = '' OR r.Data = :data)
            AND (:citta IS NULL OR :citta = '' OR LOWER(t.Citta) LIKE LOWER(CONCAT('%', :citta, '%')))
        GROUP BY s.Id_Spettacolo, s.Nome, t.Codice_Teatro, t.Nome, t.Citta, r.Data, r.Ora, t.Capacita
        """, nativeQuery = true)
    List<SpettacoloAdminProjection> filtraSpettacoliAdmin(
        @Param("query") String query,
        @Param("genere") String genere,
        @Param("data") String data,
        @Param("citta") String citta
    );

    boolean existsByCodiceTeatroAndData(String codiceTratro, LocalDate data);

    Optional<Rappresenta> findByCodiceTeatroAndIdSpettacoloAndData(String codiceTeatro, String idSpettacolo, LocalDate data);
}
