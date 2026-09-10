package org.example.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.example.dto.PrenotazioneProjection;
import org.example.dto.PrenotazioneRequest;
import org.example.model.Effettua;
import org.example.model.Prenotazione;
import org.example.model.SpecificaPosto;
import org.example.model.Utente;
import org.example.repository.EffettuaRepository;
import org.example.repository.PrenotazioneRepository;
import org.example.repository.SpecificaPostoRepository;
import org.example.repository.UtenteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

/**
 * Controller Spring REST per la gestione completa dei biglietti.
 */

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {
    // Dichiarazione repository necessari per l'accesso al database
    private final PrenotazioneRepository prenotazioneRepository;
    private final EffettuaRepository effettuaRepository;
    private final SpecificaPostoRepository specificaPostoRepository;
    private final UtenteRepository utenteRepository;

    // Iniezione della dipendenza tramite costruttore
    public PrenotazioneController(PrenotazioneRepository prenotazioneRepository, EffettuaRepository effettuaRepository,
            SpecificaPostoRepository specificaPostoRepository, UtenteRepository utenteRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.effettuaRepository = effettuaRepository;
        this.specificaPostoRepository = specificaPostoRepository;
        this.utenteRepository = utenteRepository;
    }
    
    // Endpoint POST per la creazione e registrazione di una nuova prenotazione
    @Transactional    
    @PostMapping
    public ResponseEntity<?> creaPrenotazione(@RequestBody PrenotazioneRequest request){
        // Controllo Concorrenza: Verifica se almeno uno dei posti è già occupato
        boolean postoOccupato = specificaPostoRepository.countPostiOccupati(
            request.getIdSpettacolo(),
            request.getCodiceTeatro(),
            request.getDataSpettacolo(),
            request.getPostiSelezionati()
        ) > 0;

        if(postoOccupato){
            return ResponseEntity.badRequest().body("Uno o più posti selezionati non sono più disponibili. Scegli altri posti.");
        }
        // Generazione di un codice univoco con prefisso "PR" seguito da 6 caratteri alfanumerici
        String codicePrenotazione = "PR" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        // Creazione e popolamento dell'istanza principale della prenotazione
        Prenotazione p = new Prenotazione();
        p.setCodicePrenotazione(codicePrenotazione);
        p.setIdSpettacolo(request.getIdSpettacolo());
        p.setCodiceTeatro(request.getCodiceTeatro());
        p.setnSpettatori(request.getnSpettatori());
        p.setStato("Attiva");
        p.setDataPrenotazione(LocalDateTime.now());
        p.setDataSpettacolo(request.getDataSpettacolo());
        
        prenotazioneRepository.save(p);

        // Associazione della prenotazione generata allo username dell'utente che l'ha effettuata
        Effettua e = new Effettua();
        e.setUsername(request.getUsername());
        e.setCodicePrenotazione(codicePrenotazione);
        
        effettuaRepository.save(e);

        // Ciclo sui posti selezionati dall'utente per salvare ogni associazione posto-prenotazione
        for(Integer nPosto: request.getPostiSelezionati()){
            SpecificaPosto sp = new SpecificaPosto();
            sp.setCodicePrenotazione(codicePrenotazione);
            sp.setNumeroPosto(nPosto);
            sp.setCodiceTeatro(request.getCodiceTeatro());
           
            specificaPostoRepository.save(sp);
        }

        // Assegnazione dei punti fedeltà all'utente in base alla spesa sostenuta
        if(request.getUsername() != null){
            Utente utente = utenteRepository.findById(request.getUsername()).orElse(null);
            if(utente != null){
                // Verifica se utente possiede già un punteggio, in caso contrario viene impostato a 0
                Integer puntiAttuali = utente.getPunteggio() != null ? utente.getPunteggio(): 0;
                // Incrementa punteggio sommando l'importo totale speso nella prenotazione
                utente.setPunteggio(puntiAttuali + request.getPrezzoTotale());
               
                utenteRepository.save(utente);
            }
        }
        return ResponseEntity.ok("Prenotazione completata!");
    }

    // Endpoint GET per la consultazione di tutte le prenotazioni collegate ad un profilo utente
    @GetMapping("/utente/{username}")
    public ResponseEntity<List<PrenotazioneProjection>> getPrenotazioniUtente(@PathVariable String username){
        List<PrenotazioneProjection> prenotazioni = prenotazioneRepository.findByUtenteUsername(username);
        return ResponseEntity.ok(prenotazioni);
    }

    // Endpoint PUT per modificare lo stato di una prenotazione in "Cancellata"
    @PutMapping("/{codice}/cancella")
    public ResponseEntity<?> cancellaPrenotazione(@PathVariable String codice){
       // Cerca prenotazione corrispondente al codice univoco
        return prenotazioneRepository.findByCodicePrenotazione(codice)
        .map(prenotazione ->{
            // Vieta l'annullamento di prenotazioni con stato "Chiusa"
            if("Chiusa".equalsIgnoreCase(prenotazione.getStato())){
                return ResponseEntity.badRequest().body("Non è possibile cancellare una prenotazione già chiusa.");
            }

            prenotazione.setStato("Cancellata");
            prenotazioneRepository.save(prenotazione);

            return ResponseEntity.ok("Prenotazione cancellata");
        }).orElse(ResponseEntity.notFound().build());
    }
}