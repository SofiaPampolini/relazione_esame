package org.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Interfaccia di proiezione per le prenotazioni.
 * Utilizzata come DTO in lettura per recuperare un sottoinsieme di campi direttamente dal DB,
 * evitando di caricare l'intera entità o tabelle correlate superflue.
 */

public interface PrenotazioneProjection {
    String getCodicePrenotazione();
    String getNomeSpettacolo();
    String getNomeTeatro();
    LocalDateTime getTimestampPrenotazione();
    LocalDate getDataSpettacolo();
    LocalTime getOra();
    Integer getnSpettatori();
    String getStato();
    
}