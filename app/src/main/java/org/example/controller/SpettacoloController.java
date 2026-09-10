package org.example.controller;

import org.example.dto.SpettacoloProjection;
import org.example.repository.SpettacoloRepository; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller Spring REST per la consultazione del catalogo spettacoli.
 */

@RestController
@RequestMapping("/api/spettacoli")
public class SpettacoloController {
    // Dichiarazione repository necessari per l'accesso al database    
    private final SpettacoloRepository spettacoloRepository;
   
    // Iniezione della dipendenza tramite costruttore
    public SpettacoloController(SpettacoloRepository spettacoloRepository) {
        this.spettacoloRepository = spettacoloRepository;
    }

    // Endpoint GET per la ricerca e il filtraggio dinamico degli spettacoli
    @GetMapping
    public ResponseEntity<List<SpettacoloProjection>> getSpettacoli(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(required = false) String genere,
            @RequestParam(required = false) String data,
            @RequestParam(required = false) String citta){

        // Pulizia e normalizzazione dei parametri di ricerca
        List<SpettacoloProjection> risultati = spettacoloRepository.filtraSpettacoli(
            query != null ? query.trim() : null,
            genere != null ? genere.trim() : null,
            data != null ? data.trim() : null,
            citta != null ? citta.trim() : null
        );

        return ResponseEntity.ok(risultati);
    }

    // Endpoint GET per il recupero della scheda di un singolo spettacolo tramite il suo ID
    @GetMapping("/{id}")
    public ResponseEntity<SpettacoloProjection> getSpettacoloById(@PathVariable String id){
        return spettacoloRepository.findSpettacoloById(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }
}