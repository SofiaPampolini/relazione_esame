package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entità JPA che rappresenta un'opera/spettacolo nel catalogo di sistema.
 */

@Entity
@Table(name="spettacolo")
public class Spettacolo{
    @Id
    @Column(name="Id_Spettacolo", nullable = false, columnDefinition = "CHAR(8)")
    private String id;

    @Column(name = "Nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "Genere", length = 45)
    private String genere;

    public Spettacolo() {}

    public Spettacolo(String id, String nome, String genere) {
        this.id = id;
        this.nome = nome;
        this.genere = genere;

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getGenere() {
        return genere;
    }
    public void setGenere(String genere) {
        this.genere = genere;
    }
}