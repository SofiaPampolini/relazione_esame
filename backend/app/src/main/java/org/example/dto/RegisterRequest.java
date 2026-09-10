package org.example.dto;

/**
 * DTO che mappa i dati inviati dal form di registrazione dal client.
 * Raggruppa dati e credenziali per la creazione dell'account.
 */

public class RegisterRequest {
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String password;
    private String via;
    private String civico;
    private String cap;
    private String citta;

    public String getCitta() {
        return citta;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
}
