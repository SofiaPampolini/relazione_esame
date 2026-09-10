package org.example.controller;

import java.util.List;

import org.example.dto.PostiLiberiRequest;
import org.example.repository.PostoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Spring REST per la consultazione e gestione della
 * disponibilità dei posti a sedere nei teatri.
 */

@RestController
@RequestMapping("/api/posti")
public class PostoController {
    // Dichiarazione repository necessari per l'accesso al database
    private final PostoRepository postoRepository;

    // Iniezione della dipendenza tramite costruttore
    public PostoController(PostoRepository postoRepository){
        this.postoRepository = postoRepository;
    }

    // Endpoint GET per il recupero dei numeri dei posti ancora disponibili
    @GetMapping("/liberi")
    public ResponseEntity<List<Integer>> getPostiLiberi(PostiLiberiRequest request){
        // Esecuzione query personalizzata nel repository con passaggio dei parametri presi dal DTO
        List<Integer> posti = postoRepository.findNumeriPostiLiberi(
            request.getCodiceTeatro(),
            request.getPosizione(),
            request.getIdSpettacolo()
        );

        return ResponseEntity.ok(posti);
    }
}