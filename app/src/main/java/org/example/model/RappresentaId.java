package org.example.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe che definisce la chiave primaria composta per l'entità Rappresenta.
 */

public class RappresentaId implements Serializable{
    private String codiceTeatro;
    private String idSpettacolo;
    private LocalDate data;
    
    public RappresentaId(String codiceTeatro, String idSpettacolo, LocalDate data) {
        this.codiceTeatro = codiceTeatro;
        this.idSpettacolo = idSpettacolo;
        this.data = data;
    }

    public RappresentaId() {}

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        RappresentaId that = (RappresentaId) o;
        return Objects.equals(codiceTeatro, that.codiceTeatro) &&
               Objects.equals(idSpettacolo, that.idSpettacolo) &&
               Objects.equals(data, that.data);
    }

    @Override
    public int hashCode(){
        return Objects.hash(codiceTeatro, idSpettacolo, data);
    }
    
}
