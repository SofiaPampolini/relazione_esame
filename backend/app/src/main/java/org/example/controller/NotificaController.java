package org.example.controller;

import java.util.List;

import org.example.model.Notifica;
import org.example.repository.NotificaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Spring REST per la gestione delle notifiche.
 * Espone endpoint pubblici per la consultazione lato utente.
 */

@RestController
@RequestMapping("/api/notifiche")
public class NotificaController {
    // Dichiarazione repository necessari per l'accesso al database
    private final NotificaRepository notificaRepository;
    
    // Iniezione della dipendenza tramite costruttore
    public NotificaController(NotificaRepository notificaRepository) {
        this.notificaRepository = notificaRepository;
    }

    // Endpoint GET per il recupero delle notifiche non lette dall'utente
    @GetMapping("/utente/{username}")
    public ResponseEntity<List<Notifica>> getNotificheUtente(@PathVariable String username){
        return ResponseEntity.ok(notificaRepository.findByUsernameAndLettaFalse(username));
    }
    
    // Endpoint PUT per aggiornare lo stato di una notifica segnandola come "letta"
    @PutMapping("/{id}/leggi")
    public ResponseEntity<?> segnaComeLetta(@PathVariable Long id){
        return notificaRepository.findById(id).map(notifica -> {
            notifica.setLetta(1);
            notificaRepository.save(notifica);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.badRequest().build());
    }
}
