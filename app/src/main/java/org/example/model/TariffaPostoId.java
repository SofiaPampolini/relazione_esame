package org.example.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe che rappresenta la chiave primaria composta per l'entità TariffaPosto.
 */

public class TariffaPostoId implements Serializable{
    private String codiceTeatro;
    private String posizione;

    public TariffaPostoId(String codiceTeatro, String posizione) {
        this.codiceTeatro = codiceTeatro;
        this.posizione = posizione;
    }

    public TariffaPostoId(){}

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof TariffaPostoId that)) return false;
        return Objects.equals(this.codiceTeatro, that.codiceTeatro) &&
               Objects.equals(this.posizione, that.posizione);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.codiceTeatro, posizione);
    }
}
