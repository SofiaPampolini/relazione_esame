package org.example.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.example.model.Utente;
import org.example.model.FasciaFedelta;
import org.example.model.Notifica;
import org.example.repository.FasciaFedeltaRepository;
import org.example.repository.NotificaRepository;
import org.example.repository.UtenteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Spring REST per la gestione del profilo utente.
 */

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {
    // Dichiarazione repository necessari per l'accesso al database    
    private final UtenteRepository utenteRepository;
    private final FasciaFedeltaRepository fasciaFedeltaRepository;
    private final NotificaRepository notificaRepository;
    
    // Iniezione della dipendenza tramite costruttore
    public UtenteController(UtenteRepository utenteRepository, FasciaFedeltaRepository fasciaFedeltaRepository,
            NotificaRepository notificaRepository) {
        this.utenteRepository = utenteRepository;
        this.fasciaFedeltaRepository = fasciaFedeltaRepository;
        this.notificaRepository = notificaRepository;
    }

    // Endpoint GET per il recupero del profilo di un utente tramite username
    @GetMapping("/{username}")
    public ResponseEntity<?> getUtente(@PathVariable String username){
        return utenteRepository.findById(username).map(utente -> {
            // Controllo ed eventuale azzeramento/preavviso di scadenza dei punti fedeltà
            verificaEApplicaScadenzaPunti(utente);

            // Recupero e ordinamento delle fasce fedeltà presenti nel sistema
            List<FasciaFedelta> fasce = fasciaFedeltaRepository.findAll();
            fasce.sort((f1, f2) -> Integer.compare(f2.getPunteggioMinimo(), f1.getPunteggioMinimo()));

            // Inizializzazione variabili per determinare la fascia e percentuale di sconto dell'utente
            int punti = utente.getPunteggio() != null ? utente.getPunteggio() : 0;
            String livello = "Base";
            Double sconto = 0.0;

            // Iterazione sulle fasce ordinate per identificare quella più alta raggiungibile dai punti attuali
            for(FasciaFedelta f : fasce){
                if(punti >= f.getPunteggioMinimo()){
                    livello = f.getLivello();
                    sconto = f.getPercentualeSconto();
                    break;  // Trovata la fascia più idonea, si interrompe il ciclo
                }
            }

            // Impostazione dinamica dei campi non persistenti (@Transient) dell'entità per il frontend
            utente.setLivello(livello);
            utente.setSconto(sconto);

            return ResponseEntity.ok(utente);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint PUT per l'aggiornamento delle informazioni anagrafiche dell'utente
    @PutMapping("/{username}")
    public ResponseEntity<?> aggiornaUtente(@PathVariable String username, @RequestBody Utente utenteAggiornato){
        return utenteRepository.findById(username).map(utente ->{
            // Modifica dei campi dell'utente recuperato con i nuovi dati forniti
            utente.setNome(utenteAggiornato.getNome());
            utente.setCognome(utenteAggiornato.getCognome());
            utente.setEmail(utenteAggiornato.getEmail());
            utente.setTelefono(utenteAggiornato.getTelefono());
            utente.setVia(utenteAggiornato.getVia());
            utente.setCitta(utenteAggiornato.getCitta());
            utente.setCivico(utenteAggiornato.getCivico());
            utente.setCap(utenteAggiornato.getCap());

            utenteRepository.save(utente);

            return ResponseEntity.ok("Dati aggiornati correttamente!");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint PUT per consentire all'utente di cambiare la propria password
    @PutMapping("/cambio-password")
    public ResponseEntity<?> cambiaPassword(@RequestBody Map<String, String> payload){
        // Estrazione delle credenziali passate nel corpo della richiesta
        String username = payload.get("username");
        String vecchiaPassword = payload.get("vecchiaPassword");
        String nuovaPassword = payload.get("nuovaPassword");

        // Validazione input: verifica che nessun campo obbligatorio sia nullo
        if(username == null || vecchiaPassword == null || nuovaPassword == null){
            return ResponseEntity.badRequest().body("Dati mancanti");
        }

        // Ricerca dell'utente e controllo corrispondenza con la vecchia password
        return utenteRepository.findById(username).map(utente -> {
            // Se la vecchia password e la nuova password non corrisponsono, blocca l'operazione
            if(!utente.getPassword().equals(vecchiaPassword)){
                return ResponseEntity.badRequest().body("Password errata");
            }

            utente.setPassword(nuovaPassword);

            utenteRepository.save(utente);

            return ResponseEntity.ok("Password aggiornata con successo!");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint DELETE per la rimozione definitiva dell'account dell'utente
    @DeleteMapping("/{username}")
    public ResponseEntity<?> eliminaAccount(@PathVariable String username){
        return utenteRepository.findById(username).map(utente ->{
            utenteRepository.delete(utente);
            return ResponseEntity.ok("Account rimosso correttamente");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Metodo privato per la gestione e il controllo della scadenza dei punti fedeltà
    private void verificaEApplicaScadenzaPunti(Utente utente){
        // In caso l'utente non abbia punti o il valore è 0, non è necessario proseguire
        if(utente.getPunteggio() == null || utente.getPunteggio() <= 0){
            return;
        }

        LocalDate oggi = LocalDate.now();
        List<FasciaFedelta> fasce = fasciaFedeltaRepository.findAll();

        // Ordina le fasce in ordine decrescente di punteggio minimo
        fasce.sort((f1, f2) -> Integer.compare(f2.getPunteggioMinimo(), f1.getPunteggioMinimo()));

        // Trova la fascia a cui appartiene attualmente l'utente
        int punti = utente.getPunteggio();
        FasciaFedelta fasciaUtente = null;
        for (FasciaFedelta f : fasce) {
            if (punti >= f.getPunteggioMinimo()) {
                fasciaUtente = f;
                break;
            }
        }

        // Se l'utente non appartiene ad una fascia o la sua fascia di appartenenza non ha scadenza, interrompe l'esecuzione
        if (fasciaUtente == null || fasciaUtente.getDataScadenza() == null) {
            return;
        }

        LocalDate scadenza = fasciaUtente.getDataScadenza();

        // 1. Notifica di preavviso inviata a partire da 7 giorni prima fino al giorno della scadenza 
        if ((oggi.isAfter(scadenza.minusDays(7)) || oggi.isEqual(scadenza.minusDays(7))) && !oggi.isAfter(scadenza)) {
            String msgAvviso = "Attenzione: la fascia di punteggio " + fasciaUtente.getLivello() +
                            " scadrà il " + scadenza +
                            ". I tuoi punti verranno azzerati al termine della validità.";
            // Invio della notifica solo se non ne è già stata creata una identica per l'utente
            if (!notificaRepository.existsByUsernameAndMessaggio(utente.getUsername(), msgAvviso)) {
                creaNotifica(utente.getUsername(), msgAvviso);
            }
        }

        // 2. Azzeramento punti post-scadenza: se la data attuale ha superato la data di scadenza
        if (oggi.isAfter(scadenza)) {
            utente.setPunteggio(0);
            utenteRepository.save(utente);

            String msgScaduto = "La fascia di punteggi " + fasciaUtente.getLivello() +
                                " è scaduta in data " + scadenza +
                                ". I tuoi punti accumulati sono stati azzerati.";
            // Invio della notifica di conferma azzeramento solo se non ne è già stata inviata in precedenza
            if (!notificaRepository.existsByUsernameAndMessaggio(utente.getUsername(), msgScaduto)) {
                creaNotifica(utente.getUsername(), msgScaduto);
            }
        }
    }

    // Metodo privato per la creazione e persistenza rapida di una nuova notifica
    private void creaNotifica(String username, String messaggio){
        Notifica n = new Notifica();

        n.setUsername(username);
        n.setMessaggio(messaggio);
        n.setLetta(0);
        notificaRepository.save(n);
    }
}