package org.example.dto;

import org.example.model.Rappresenta;
import org.example.model.Spettacolo;

/**
 * DTO utilizzato dall'amministratore per registrare contemporaneamente
 * le informazioni di uno spettacolo e la sua associazione con una replica teatrale.
 */

public class SpettacoloRequest {
    private Spettacolo spettacolo;
    private Rappresenta rappresentazione;
    
    public Spettacolo getSpettacolo() {
        return spettacolo;
    }
    public void setSpettacolo(Spettacolo spettacolo) {
        this.spettacolo = spettacolo;
    }
    public Rappresenta getRappresentazione() {
        return rappresentazione;
    }
    public void setRappresentazione(Rappresenta rappresentazione) {
        this.rappresentazione = rappresentazione;
    }
}
