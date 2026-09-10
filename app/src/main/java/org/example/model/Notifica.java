package org.example.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entità JPA per la memorizzazione e gestione delle notifiche indirizzate agli utenti.
 */

@Entity
@Table(name = "notifica")
public class Notifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Messaggio")
    private String messaggio;

    @Column(name = "Letta", columnDefinition = "TINYINT")
    private Integer letta = 0;

    @Column(name = "Data_Creazione")
    private LocalDateTime dataCreazione = LocalDateTime.now();

    public Notifica() {}

    public Notifica(Integer id, String username, String messaggio, Integer letta, LocalDateTime dataCreazione) {
        this.id = id;
        this.username = username;
        this.messaggio = messaggio;
        this.letta = letta;
        this.dataCreazione = dataCreazione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public Integer getLetta() {
        return letta;
    }

    public void setLetta(Integer letta) {
        this.letta = letta;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    
}
