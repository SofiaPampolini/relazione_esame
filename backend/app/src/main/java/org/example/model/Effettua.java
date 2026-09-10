package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Column;

/** 
 * Entità JPA che modella la relazione "Effettua" tra un Utente e una Prenotazione.
 * Utilizza la chiave composta definita nella classe EffettuaId.
 */

@Entity
@IdClass(EffettuaId.class)
@Table(name="effettua")
public class Effettua {
    @Id
    @Column(name = "Utente")
    private String username;

    @Id
    @Column(name = "Codice_Prenotazione", columnDefinition = "CHAR(8)")
    private String codicePrenotazione;

    public Effettua(String username, String codicePrenotazione) {
        this.username = username;
        this.codicePrenotazione = codicePrenotazione;
    }

    public Effettua() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }
}
