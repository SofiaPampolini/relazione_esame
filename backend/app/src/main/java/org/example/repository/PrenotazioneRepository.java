package org.example.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.example.dto.PrenotazioneProjection;
import org.example.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interfaccia Repository per l'entità Prenotazione.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria di tipo String.
 */

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, String>{
    // Recupero dello storico prenotazioni dettagliato di uno specifico utente
    @Query(value = """
            SELECT DISTINCT p.Codice_Prenotazione AS codicePrenotazione, 
                   p.Data_Prenotazione AS timestampPrenotazione,
                   s.Nome AS nomeSpettacolo, t.Nome AS nomeTeatro,
                   p.Data_Spettacolo AS dataSpettacolo, r.Ora AS ora,
                   p.N_Spettatori AS nSpettatori, p.Stato AS stato
            FROM prenotazione p
            JOIN effettua e ON p.Codice_Prenotazione=e.Codice_Prenotazione
            JOIN rappresenta r ON p.Codice_Teatro=r.Codice_Teatro
                 AND p.Id_Spettacolo=r.Id_Spettacolo 
                 AND p.Data_Spettacolo=r.Data
            JOIN spettacolo s ON r.Id_Spettacolo=s.Id_Spettacolo
            JOIN teatro t ON r.Codice_Teatro=t.Codice_Teatro
            WHERE e.Utente=:username
            """, nativeQuery = true
    )
    List<PrenotazioneProjection> findByUtenteUsername(@Param("username") String username);
    Optional<Prenotazione> findByCodicePrenotazione(String codice);
    List<Prenotazione> findByIdSpettacoloAndCodiceTeatroAndDataSpettacolo(String idSpettacolo, String codiceTeatro, LocalDate dataSpettacolo);
}
