package org.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.example.model.SpecificaPosto;
import org.example.model.SpecificaPostoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interfaccia Repository per l'entità SpecificaPosto.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria composta SpecificaPostoId.
 */

public interface SpecificaPostoRepository extends JpaRepository<SpecificaPosto, SpecificaPostoId>{
    // Verifica se l'elenco dei posti per la data e spettacolo passati come parametro risultano già occupati
    @Query(value = """
            SELECT COUNT(*) > 0
            FROM specifica_posto sp
            JOIN prenotazione p ON sp.Codice_Prenotazione = p.Codice_Prenotazione
            WHERE p.Id_Spettacolo = :idSpettacolo
              AND p.Codice_Teatro = :codiceTeatro
              AND p.Data_Spettacolo = :dataSpettacolo
              AND p.Stato = 'Attiva'
              AND sp.Numero_Posto IN :posti
            """, nativeQuery = true)
    int countPostiOccupati(
        @Param("idSpettacolo") String idSpettacolo,
        @Param("codiceTeatro") String codiceTeatro,
        @Param("dataSpettacolo") LocalDate dataSpettacolo,
        @Param("posti") List<Integer> posti
    );
}
