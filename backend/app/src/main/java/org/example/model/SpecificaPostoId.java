package org.example.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe che rappresenta la chiave primaria composta per l'entità SpecificaPosto.
 */

public class SpecificaPostoId implements Serializable {
    private String codicePrenotazione;
    private Integer numeroPosto;
    private String codiceTeatro;

    public SpecificaPostoId() {}

    public SpecificaPostoId(String codicePrenotazione, Integer numeroPosto, String codiceTeatro) {
        this.codicePrenotazione = codicePrenotazione;
        this.numeroPosto = numeroPosto;
        this.codiceTeatro = codiceTeatro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificaPostoId that = (SpecificaPostoId) o;
        return Objects.equals(codicePrenotazione, that.codicePrenotazione) &&
               Objects.equals(numeroPosto, that.numeroPosto) &&
               Objects.equals(codiceTeatro, that.codiceTeatro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codicePrenotazione, numeroPosto, codiceTeatro);
    }
}
