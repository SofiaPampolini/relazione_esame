package org.example.repository;

import java.util.List;

import org.example.model.Posto;
import org.example.model.PostoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interfaccia Repository per l'entità Posto.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria composta PostoId.
 */

public interface PostoRepository extends JpaRepository<Posto, PostoId> {
    // Calcolo del numero di posti liberi per una data rappresentazione
    @Query(value = """
        SELECT Numero 
        FROM posto
        WHERE Codice_Teatro = :codiceTeatro
            AND Posizione = :posizione
            AND Numero NOT IN (
                SELECT sp.Numero_Posto
                FROM specifica_posto sp
                JOIN prenotazione p ON sp.Codice_Prenotazione = p.Codice_Prenotazione
                WHERE p.Codice_Teatro = :codiceTeatro
                    AND p.Id_Spettacolo = :idSpettacolo
                    AND p.Stato != 'Cancellata'
            )
        ORDER BY Numero
        """, nativeQuery = true)
    List<Integer> findNumeriPostiLiberi(
        @Param("codiceTeatro") String codiceTeatro,
        @Param("posizione") String posizione,
        @Param("idSpettacolo") String idSpettacolo
    );
}
