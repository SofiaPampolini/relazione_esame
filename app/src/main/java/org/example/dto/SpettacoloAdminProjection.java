package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Interfaccia di proiezione riservata al pannello di amministraizione.
 * Estende i dati informativi dello spettacolo includendo il numero di posti ancora disponibili.
 */

public interface SpettacoloAdminProjection {
    String getIdSpettacolo();
    String getNome();
    String getNomeTeatro();
    String getCodiceTeatro();
    String getCittaTeatro();
    LocalDate getData();
    LocalTime getOra();
    Integer getPostiLiberi();
}
