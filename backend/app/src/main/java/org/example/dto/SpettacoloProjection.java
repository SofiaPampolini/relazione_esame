package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Interfaccia di proiezione dedicata alla visualizzazione pubblica del catalogo spettacoli.
 */

public interface SpettacoloProjection {
    String getId();
    String getNome();
    String getGenere();
    LocalDate getData();
    LocalTime getOra();
    String getCodiceTeatro();
    String getNomeTeatro();
    String getCittaTeatro();
}