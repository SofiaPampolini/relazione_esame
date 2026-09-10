package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * Entità JPA che rappresenta un posto a sedere all'interno di un teatro.
 * Utilizza la chiave composta definita nella classe PostoId.
 */

@Entity
@Table(name="posto")
@IdClass(PostoId.class)
public class Posto {
    @Id
    @Column(name="Numero")
    private Integer numero;

    @Column(name = "Codice_Teatro", columnDefinition = "CHAR(8)")
    private String codiceTeatro;

    @Column(name = "Posizione", length = 45)
    private String posizione;
    

    public Posto() {}

    public Posto(Integer numero, String codiceTeatro, String posizione) {
        this.numero = numero;
        this.codiceTeatro = codiceTeatro;
        this.posizione = posizione;
        
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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

}
