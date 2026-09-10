package org.example.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Column;

/**
 * Entità JPA che modella la tabella "specifica_posto", indicante i singoli posti assegnati ad una prenotazione.
 * Utilizza la chiave composta definita nella classe SpecificaPostoId.
 */

@Entity
@IdClass(SpecificaPostoId.class)
@Table(name="specifica_posto")
public class SpecificaPosto {
    @Id
    @Column(name="Codice_Prenotazione", columnDefinition = "CHAR(8)")
    private String codicePrenotazione;

    @Id
    @Column(name = "Numero_Posto")
    private Integer numeroPosto;

    @Column(name = "Codice_Teatro", columnDefinition = "CHAR(8)")
    private String codiceTeatro;

    public SpecificaPosto() {}

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public Integer getNumeroPosto() {
        return numeroPosto;
    }

    public void setNumeroPosto(Integer numeroPosto) {
        this.numeroPosto = numeroPosto;
    }

    public String getCodiceTeatro() {
        return codiceTeatro;
    }

    public void setCodiceTeatro(String codiceTeatro) {
        this.codiceTeatro = codiceTeatro;
    }
}