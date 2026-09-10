package org.example.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entità JPA per la mappatura dei livelli e delle fasce del programma fedeltà.
 */

@Entity
@Table(name = "punteggio")
public class FasciaFedelta {
    @Id
    @Column(name = "Livello")
    private String livello;

    @Column(name = "Punteggio_Minimo")
    private Integer punteggioMinimo;

    @Column(name = "Percentuale_Sconto", columnDefinition = "DECIMAL(5, 2)")
    private Double percentualeSconto;

    @Column(name = "Data_Scadenza")
    private LocalDate dataScadenza;

    public FasciaFedelta(String livello, Integer punteggioMinimo, Double percentualeSconto,
            LocalDate dataScadenza) {
        this.livello = livello;
        this.punteggioMinimo = punteggioMinimo;
        this.percentualeSconto = percentualeSconto;
        this.dataScadenza = dataScadenza;
    }

    public FasciaFedelta() {}

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public Integer getPunteggioMinimo() {
        return punteggioMinimo;
    }

    public void setPunteggioMinimo(Integer punteggioMinimo) {
        this.punteggioMinimo = punteggioMinimo;
    }

    public Double getPercentualeSconto() {
        return percentualeSconto;
    }

    public void setPercentualeSconto(Double percentualeSconto) {
        this.percentualeSconto = percentualeSconto;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
}
