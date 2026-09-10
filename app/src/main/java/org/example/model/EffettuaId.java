package org.example.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe che rappresenta la chiave primaria composta per l'entità Effettua.
 */

public class EffettuaId implements Serializable{
    // Campi della chiave composta
    private String username;
    private String codicePrenotazione;
    
    public EffettuaId(String username, String codicePrenotazione) {
        this.username = username;
        this.codicePrenotazione = codicePrenotazione;
    }

    public EffettuaId() {}

    // Override metodo equals per confrontare l'uguaglianza tra due istanze
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        EffettuaId that = (EffettuaId)o;
        return Objects.equals(username, that.username) &&
               Objects.equals(codicePrenotazione, that.codicePrenotazione);
    }
    
    // Override metodo hashCode per la generazione di un codice hash univoco basato sui campi della chiave
    @Override
    public int hashCode(){
        return Objects.hash(username, codicePrenotazione);
    }
}
