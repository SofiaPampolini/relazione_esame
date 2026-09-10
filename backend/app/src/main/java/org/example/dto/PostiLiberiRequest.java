package org.example.dto;

/**
 * DTO che rappresenta i criteri di filtro inviati dal client
 * per verificare la disponibilità dei posti liberi per un dato spettacolo e settore teatrale.
 */
public class PostiLiberiRequest {
    private String codiceTeatro;
    private String posizione;
    private String idSpettacolo;

    public PostiLiberiRequest() {}

    public String getCodiceTeatro() {
        return codiceTeatro;
    }

    public void setCodiceTeatro(String codiceTeatro) {
        this.codiceTeatro = codiceTeatro;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public String getIdSpettacolo() {
        return idSpettacolo;
    }

    public void setIdSpettacolo(String idSpettacolo) {
        this.idSpettacolo = idSpettacolo;
    }
}
