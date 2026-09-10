package org.example.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Entità JPA per la gestione del profilo utente, credenziali di accesso e attributi calcolati per il programma fedeltà.
 */

@Entity
@Table(name="utente")
public class Utente {
    @Id 
    @Column(name="Username", nullable = false, length=45)
    private String username;

    @Column(name = "Nome", nullable = false, length = 45)
    private String nome;

    @Column(name = "Cognome", nullable = false, length = 45)
    private String cognome;

    @Column(name = "Via", length = 45)
    private String via;

    @Column(name = "Civico", length = 10)
    private String civico;

    @Column(name = "Cap", length = 10)
    private String cap;

    @Column(name="Citta", length = 45)
    private String citta;

    @Column(name = "Telefono", length = 20)
    private String telefono;

    @Column(name = "Email", nullable = false, length = 45)
    private String email;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Punteggio")
    private Integer punteggio = 0;

    @Column(name = "Tipo", nullable = false, columnDefinition = "TINYINT")
    private Integer tipo = 0; // 0 = Utente normale, 1 = Amministratore

    @Transient    // Il campo è calcolato dinamicamente, non è memorizzato in una colonna
    private String livello;

    @Transient
    private Double sconto;

    public Double getSconto() {
        return sconto;
    }

    public void setSconto(Double sconto) {
        this.sconto = sconto;
    }

    public Utente(String username, String nome, String cognome, String via, String civico, String cap, String telefono,
            String email, String password, Integer punteggio, Integer tipo, String livello, String citta) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.punteggio = punteggio;
        this.tipo = tipo;
        this.livello = livello;
        this.citta=citta;
    }

    public Utente(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(Integer punteggio) {
        this.punteggio = punteggio;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }
    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }
}
