package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entità JPA che rappresenta le informazioni anagrafiche e la capienza di un teatro.
 */

@Entity
@Table(name = "teatro")
public class Teatro {
    @Id
    @Column(name = "Codice_Teatro", columnDefinition = "CHAR(8)")
    private String codiceTeatro;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Capacita")
    private Integer capacita;

    @Column(name = "Via")
    private String via;

    @Column(name = "Civico")
    private String civico;

    @Column(name = "Cap")
    private String cap;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Email")
    private String email;

    @Column(name = "Citta")
    private String citta;

    public Teatro(String codiceTeatro, String nome, Integer capacita, String via, String civico, String cap,
            String telefono, String email, String citta) {
        this.codiceTeatro = codiceTeatro;
        this.nome = nome;
        this.capacita = capacita;
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.telefono = telefono;
        this.email = email;
        this.citta = citta;
    }

    public Teatro() {}

    public String getCodiceTeatro() {
        return codiceTeatro;
    }

    public void setCodiceTeatro(String codiceTeatro) {
        this.codiceTeatro = codiceTeatro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCapacita() {
        return capacita;
    }

    public void setCapacita(Integer capacita) {
        this.capacita = capacita;
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

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }    
}
