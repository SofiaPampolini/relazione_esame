package org.example.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;

import org.example.dto.LoginRequest;
import org.example.dto.RegisterRequest;
import org.example.model.Utente;
import org.example.repository.UtenteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Controller Spring REST per la gestione dell'autenticazione.
 * Espone endpoint pubblici per la registrazione e il login.
 */
@RestController
@RequestMapping("/api/auth")

public class AuthController {
    // Dichiarazione repository necessari per l'accesso al database
    private final UtenteRepository utenteRepository;

    // Chiave segreta utilizzata per firmare e verificare l'integrità dei token JWT
    private final Key SECRET_KEY = Keys.hmacShaKeyFor("ChiaveSegretaPerIlControlloSicurezza".getBytes());

    // Iniezione della dipendenza tramite costruttore
    public AuthController(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    // Endpoint POST per la registrazione di un nuovo utente
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        // Controllo di univocità dello username inserito
        if(utenteRepository.existsById(request.getUsername())){
            return ResponseEntity.badRequest().body("Username già in uso");
        }

        // Creazione e popolamento della nuova istanza dell'entità Utente
        Utente u = new Utente();
        u.setUsername(request.getUsername());
        u.setNome(request.getNome());
        u.setCognome(request.getCognome());
        u.setEmail(request.getEmail());
        u.setTelefono(request.getTelefono());
        u.setPassword(request.getPassword());
        u.setVia(request.getVia());
        u.setCivico(request.getCivico());
        u.setCap(request.getCap());
        u.setCitta(request.getCitta());

        // Impostazione dei valori predefiniti
        u.setTipo(0);   // 0 = Utente standard, 1 = amministratore
        u.setLivello("Bronzo");
        u.setPunteggio(0);

        utenteRepository.save(u);
        return ResponseEntity.ok("Registrazione avvenuta con sucesso");
    }

    // Endpoint POST per effettuare il login dell'utente e ottenere un token JWT
    @PostMapping("/login")
    public ResponseEntity<?> register(@RequestBody LoginRequest request){
        // Ricerca dell'utente nel DB tramite username
        return utenteRepository.findById(request.getUsername())
       
       // Filtraggio del risultato ottenuto: verifica che la password fornita coincida con quella memorizzata
        .filter(u -> u.getPassword().equals(request.getPassword()))
       
       // Se l'utente esiste e la password è corretta, mappa il risultato in una ResponseEntity di successo
        .<ResponseEntity<?>>map(u -> {

            // Conversione del campo numerico 'tipo' nella stringa Ruolo per l'Interceptor
            String ruolo = (u.getTipo() == 1) ? "ADMIN" : "USER";

            // Generazione del Token JWT contenente i dati di autenticazione
            String token = Jwts.builder()                                       
                .setSubject(u.getUsername())                                     // Identificativo utente
                .claim("tipo", u.getTipo())                                // Claim personalizzato con il tipo numerico
                .claim("ruolo", ruolo)                                     // Claim personalizzato per il controllo dei ruoli
                .setIssuedAt(new Date())                                        // Data e ora di emissione del token
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Impostazione scadenza a 24h
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)                 // Firma del token con algoritmo HMAC-SHA256 e chiave segreta
                .compact();                                                     // Generazione stringa finale del token

            // Rimozione password per sicurezza prima dell'invio della risposta
            u.setPassword(null);

            // Costruzione risposta JSON contenente token e oggetto utente
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("utente", u);

            return ResponseEntity.ok(response);
        })
        // In caso non venga trovato l'utente o la password non corrisponde, restituisce HTTP 401 UNAUTHORIZED
        .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non valide"));
    }
}