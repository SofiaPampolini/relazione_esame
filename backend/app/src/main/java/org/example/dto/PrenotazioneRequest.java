package org.example.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO per incapsulare i dati inviati dal client al backend
 * durante la creazione di una nuova prenotazione di biglietti.
 */

public class PrenotazioneRequest {
    private String username;
    private String idSpettacolo;
    private String codiceTeatro;
    private Integer nSpettatori;
    private List<Integer> postiSelezionati = new ArrayList<>();
    private Integer prezzoTotale;
    private LocalDate dataSpettacolo;

    public Integer getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(Integer prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public PrenotazioneRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getnSpettatori() {
        return nSpettatori;
    }

    public void setnSpettatori(Integer nSpettatori) {
        this.nSpettatori = nSpettatori;
    }

    public List<Integer> getPostiSelezionati() {
        return postiSelezionati;
    }

    public void setPostiSelezionati(List<Integer> postiSelezionati) {
        this.postiSelezionati = postiSelezionati;
    }

      public LocalDate getDataSpettacolo() {
        return dataSpettacolo;
    }

    public void setDataSpettacolo(LocalDate dataSpettacolo) {
        this.dataSpettacolo = dataSpettacolo;
    }
}
