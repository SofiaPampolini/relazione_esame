package org.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.example.model.FasciaFedelta;
import org.example.repository.FasciaFedeltaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

/**
 * Controller Spring REST per la gestione delle operazioni sulle fasce fedeltà.
 * Espone endpoint per le operazioni CRUD.
 */

@RestController
@RequestMapping("/api/admin/fasce")
public class FasciaFedeltaController {
    // Dichiarazione repository necessari per l'accesso al database
    private final FasciaFedeltaRepository fasciaFedeltaRepository;

    // Iniezione della dipendenza tramite costruttore
    public FasciaFedeltaController(FasciaFedeltaRepository fasciaFedeltaRepository) {
        this.fasciaFedeltaRepository = fasciaFedeltaRepository;
    }

    // Endpoint GET per il recupero dell'elenco completo di tutte le fasce
    @GetMapping
    public List<FasciaFedelta> getAll(){
        return fasciaFedeltaRepository.findAll();
    }

    // Endpoint POST per registrare una nuova fascia
    @Transactional
    @PostMapping
    public ResponseEntity<?> creaFascia(@RequestBody FasciaFedelta fascia){
        // Verifica che il livello non sia nullo o non sia già presente
        if(fascia.getLivello() == null || fasciaFedeltaRepository.existsById(fascia.getLivello())){
            return ResponseEntity.badRequest().body("Fascia livello non valida o già esistente.");
        }

        // Controlla cha la data di scadenza, se definita, non sia antecedente alla data corrente
        if(fascia.getDataScadenza() != null && fascia.getDataScadenza().isBefore(LocalDate.now())){
            return ResponseEntity.badRequest().body("La data di scadenza non può essere precedente a oggi");
        }

        fasciaFedeltaRepository.save(fascia);

        return ResponseEntity.ok(fascia);
    }

    // Endpoint PUT per l'aggiornamento dei parametri di una fascia esistente
    @Transactional
    @PutMapping("/{livello}")
    public ResponseEntity<?> aggiorna(@PathVariable String livello, @RequestBody FasciaFedelta dettagli){
        // Controllo validità nuova data di scadenza
        if(dettagli.getDataScadenza() != null && dettagli.getDataScadenza().isBefore(LocalDate.now())){
            return ResponseEntity.badRequest().body("La data di scadenza non può essere precedente a oggi");
        }

        // Ricerca del record tramite la chiave primaria "livello" e aggiornamento dei dati
        return fasciaFedeltaRepository.findById(livello).map(fascia ->{
            fascia.setPunteggioMinimo(dettagli.getPunteggioMinimo());
            fascia.setPercentualeSconto(dettagli.getPercentualeSconto());
            fascia.setDataScadenza(dettagli.getDataScadenza());

            fasciaFedeltaRepository.save(fascia);

            return ResponseEntity.ok(fascia);
        }).orElse(ResponseEntity.badRequest().build());
    }

    // Endpoint DELETE per la rimozione di una fascia fedeltà
    @Transactional
    @DeleteMapping("/{livello}")
    public ResponseEntity<?> elimina(@PathVariable String livello){
        // Verifica la presenza del record prima della cancellazione
        if(fasciaFedeltaRepository.existsById(livello)){
            fasciaFedeltaRepository.deleteById(livello);

            return ResponseEntity.ok("Livello eliminato con successo");
        }

        return ResponseEntity.notFound().build();
    }
}
