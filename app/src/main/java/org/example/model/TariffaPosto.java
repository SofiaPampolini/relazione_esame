package org.example.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * Entità JPA per la memorizzazione delle tariffe applicate alle diverse sezioni del teatro.
 * Utilizza la chiave composta definita nella classe TariffaPostoId.
 */

@Entity
@Table(name="tariffa_posto")
@IdClass(TariffaPostoId.class)
public class TariffaPosto {
    @Id
    @Column(name = "Codice_Teatro", columnDefinition = "CHAR(8")
    private String codiceTeatro;

    @Column(name = "Posizione", length=45)
    private String posizione;

    @Column(name = "Prezzo")
    private BigDecimal prezzo;

    public TariffaPosto() {}

    public TariffaPosto(String codiceTeatro, String posizione, BigDecimal prezzo) {
        this.codiceTeatro = codiceTeatro;
        this.posizione = posizione;
        this.prezzo = prezzo;
    }

    public String getCodiceTeatro() {
        return codiceTeatro;
    }

    public void setCodiceTeatro(String codiceTeatro) {
        this.codiceTeatro = codiceTeatro;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }
}
