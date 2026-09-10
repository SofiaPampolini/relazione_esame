package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/** 
 * DTO utilizzato per incapsulare la richiesta di modifica della data 
 * e/o dell'orario di una replica di uno spettacolo in un dato teatro.
 */ 

public class ModificaSpettacoloRequest {
    private String codiceTeatro;
    private String idSpettacolo;
    private LocalDate vecchiaData;
    private LocalDate nuovaData;
    private LocalTime nuovaOra;

    public String getCodiceTeatro() {
        return codiceTeatro;
    }
    public void setCodiceTeatro(String codiceTeatro) {
        this.codiceTeatro = codiceTeatro;
    }
    public String getIdSpettacolo() {
        return idSpettacolo;
    }
    public void setIdSpettacolo(String idSpettacolo) {
        this.idSpettacolo = idSpettacolo;
    }
    public LocalDate getVecchiaData() {
        return vecchiaData;
    }
    public void setVecchiaData(LocalDate vecchiaData) {
        this.vecchiaData = vecchiaData;
    }
    public LocalDate getNuovaData() {
        return nuovaData;
    }
    public void setNuovaData(LocalDate nuovaData) {
        this.nuovaData = nuovaData;
    }
    public LocalTime getNuovaOra() {
        return nuovaOra;
    }
    public void setNuovaOra(LocalTime nuovaOra) {
        this.nuovaOra = nuovaOra;
    }
}
