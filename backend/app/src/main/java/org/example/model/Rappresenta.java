package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * Entità JPA che modella la programmazione (replica) di uno spettacolo all'interno di un teatro.
 * Utilizza la chiave composta definita nella classe RappresentaId.
 */

@Entity
@Table(name="rappresenta")
@IdClass(RappresentaId.class)
public class Rappresenta {
    @Id
    @Column(name="Codice_Teatro", columnDefinition = "CHAR(8)")
    private String codiceTeatro;

    @Id
    @Column(name = "Id_Spettacolo", columnDefinition = "CHAR(8)")
    private String idSpettacolo;

    @Id
    @Column(name = "Data")
    private LocalDate data;
    
    @Column(name="Ora")
    private LocalTime ora;

    @Column(name = "Stato", length = 45)
    private String stato = "Confermato";

    public Rappresenta(String codiceTeatro, String idSpettacolo, LocalDate data, LocalTime ora, String stato) {
        this.codiceTeatro = codiceTeatro;
        this.idSpettacolo = idSpettacolo;
        this.data = data;
        this.ora = ora;
        this.stato = stato;
    }

    public Rappresenta() {}

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

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }


    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
