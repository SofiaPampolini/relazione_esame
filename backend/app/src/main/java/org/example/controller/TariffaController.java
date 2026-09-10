package org.example.controller;

import java.util.List;

import org.example.model.TariffaPosto;
import org.example.repository.TariffaPostoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Spring REST per la consultazione dei prezzi e delle tariffe applicate ai posti dei teatri.
 */

@RestController
@RequestMapping("/api/tariffe")
public class TariffaController {
    // Dichiarazione repository necessari per l'accesso al database    
    private final TariffaPostoRepository tariffaPostoRepository;

    // Iniezione della dipendenza tramite costruttore
    public TariffaController(TariffaPostoRepository tariffaPostoRepository){
        this.tariffaPostoRepository = tariffaPostoRepository;
    }

    // Endpoint GET per il recupero del listino completo delle tariffe associate ad un determinato teatro
    @GetMapping("/{codiceTeatro}")
    public ResponseEntity<List<TariffaPosto>> getTariffe(@PathVariable String codiceTeatro){
            List<TariffaPosto> tariffe = tariffaPostoRepository.findByCodiceTeatro(codiceTeatro);
            return ResponseEntity.ok(tariffe);
    }
}