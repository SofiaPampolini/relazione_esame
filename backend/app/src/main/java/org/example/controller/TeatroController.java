package org.example.controller;

import java.util.List;

import org.example.model.Teatro;
import org.example.repository.TeatroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Spring REST per la consultazione delle informazioni relative ai teatri gestiti nel sistema.
 */

@RestController
@RequestMapping("/api/teatri")
public class TeatroController {
    // Dichiarazione repository necessari per l'accesso al database    
    private final TeatroRepository teatroRepository;

    // Iniezione della dipendenza tramite costruttore
    public TeatroController(TeatroRepository teatroRepository) {
        this.teatroRepository = teatroRepository;
    }

    // Endpoint GET per il recupero dell'elenco completo di tutti i teatri registrati
    @GetMapping
    public ResponseEntity<List<Teatro>> getTuttiTeatri(){
        List<Teatro> teatri = teatroRepository.findAllByOrderByNomeAscOrderByCittaAsc();
        return ResponseEntity.ok(teatri);
    }
}