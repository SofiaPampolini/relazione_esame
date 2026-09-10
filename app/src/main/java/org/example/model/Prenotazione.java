package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entità JPA per la gestione e la persistenza delle prenotazioni effettuate dagli utenti.
 */

@Entity
@Table(name="prenotazione")
public class Prenotazione {
    @Id
    @Column(name="Codice_Prenotazione", columnDefinition = "CHAR(8)")
    private String codicePrenotazione;

    @Column(name = "Id_Spettacolo", columnDefinition = "CHAR(8)")
    private String idSpettacolo;

    @Column(name = "Codice_Teatro", columnDefinition = "CHAR(8)")
    private String codiceTeatro;

    @Column(name = "Stato", length = 45)
    private String stato;

    @Column(name = "Data_Prenotazione")
    private LocalDateTime dataPrenotazione;

    @Column(name = "N_Spettatori")
    private Integer nSpettatori;

    @Column(name = "Data_Spettacolo")
    private LocalDate dataSpettacolo;

    public Prenotazione(String codicePrenotazione, String idSpettacolo, String codiceTeatro, String stato,
            LocalDateTime dataPrenotazione, Integer nSpettatori, LocalDate dataSpettacolo) {
        this.codicePrenotazione = codicePrenotazione;
        this.idSpettacolo = idSpettacolo;
        this.codiceTeatro = codiceTeatro;
        this.stato = stato;
        this.dataPrenotazione = dataPrenotazione;
        this.nSpettatori = nSpettatori;
        this.dataSpettacolo = dataSpettacolo;
    }

    public Prenotazione() {}

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public String getIdSpettacolo() {
        return idSpettacolo;
    }

    public void setIdSpettacolo(String idSpettacolo) {
        this.idSpettacolo = idSpettacolo;
    }

    public String getCodiceTeatro() {
        return codiceTeatro;
    }

    public void setCodiceTeatro(String codiceTeatro) {
        this.codiceTeatro = codiceTeatro;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDateTime dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public Integer getnSpettatori() {
        return nSpettatori;
    }

    public void setnSpettatori(Integer nSpettatori) {
        this.nSpettatori = nSpettatori;
    }

        public LocalDate getDataSpettacolo() {
        return dataSpettacolo;
    }

    public void setDataSpettacolo(LocalDate dataSpettacolo) {
        this.dataSpettacolo = dataSpettacolo;
    }
}
