package org.example.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe per la rappresentazione della chiave primaria composta dell'entità Posto.
 */

public class PostoId implements Serializable{
    private Integer numero;
    private String codiceTeatro;
    
    public PostoId(Integer numero, String codiceTeatro) {
        this.numero = numero;
        this.codiceTeatro = codiceTeatro;
    }

    public PostoId() {}

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
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostoId postoId=(PostoId) o;
        return Objects.equals(numero, postoId.numero) &&
               Objects.equals(codiceTeatro, postoId.codiceTeatro);
    }

    @Override
    public int hashCode(){
        return Objects.hash(numero, codiceTeatro);
    }
}
