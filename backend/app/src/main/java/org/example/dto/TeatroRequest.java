package org.example.dto;

import java.util.List;

import org.example.model.Posto;
import org.example.model.TariffaPosto;
import org.example.model.Teatro;

/**
 * DTO utilizzato dall'amministratore per la configurazione completa di un teatro.
 * Permette l'invio in un'unica richiesta delle informazioni della struttura, tariffe posti e mappa della sala.
 */

public class TeatroRequest {
    private Teatro teatro;
    private List<TariffaPosto> tariffe;
    private List<Posto> posti;
    
    public Teatro getTeatro() {
        return teatro;
    }
    public void setTeatro(Teatro teatro) {
        this.teatro = teatro;
    }
    public List<TariffaPosto> getTariffe() {
        return tariffe;
    }
    public void setTariffe(List<TariffaPosto> tariffe) {
        this.tariffe = tariffe;
    }
    public List<Posto> getPosti() {
        return posti;
    }
    public void setPosti(List<Posto> posti) {
        this.posti = posti;
    }
}
