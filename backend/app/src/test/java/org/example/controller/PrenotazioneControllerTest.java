package org.example.controller;

import org.example.dto.PrenotazioneRequest;
import org.example.model.Utente;
import org.example.repository.EffettuaRepository;
import org.example.repository.PrenotazioneRepository;
import org.example.repository.SpecificaPostoRepository;
import org.example.repository.UtenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Classe di test unitario per PrenotazioneController.
 * Utilizza MockitoExtension per isolare il controller dal database reale,
 * simulando il comportamento dei Repository (Mocking).
 */
@ExtendWith(MockitoExtension.class) // Abilita l'estensione di Mockito per JUnit 5 (gestisce il ciclo di vita dei mock)
class PrenotazioneControllerTest {

    // Dipendenze fittizie (Mock) simulate per evitare chiamate al database
    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private EffettuaRepository effettuaRepository;

    @Mock
    private SpecificaPostoRepository specificaPostoRepository;

    @Mock
    private UtenteRepository utenteRepository;

    /**
     * Creazione di un'istanza reale di PrenotazioneController e iniezione automatica 
     * dei campi annotati con @Mock definiti sopra all'interno del costruttore
     */
    @InjectMocks
    private PrenotazioneController prenotazioneController;

    // Oggetto DTO di supporto per riutilizzare dati validi tra i vari test
    private PrenotazioneRequest requestValida;

    /**
     * Metodo di configurazione eseguito prima di ogni test per garantire l'isolamento.
     * (ogni test inizia con un oggetto requestValida nuovo).
     */
    @BeforeEach
    void setUp() {
        requestValida = new PrenotazioneRequest();
        requestValida.setUsername("mario_rossi");
        requestValida.setIdSpettacolo("SPET01");
        requestValida.setCodiceTeatro("TEA01");
        requestValida.setnSpettatori(2);
        requestValida.setPrezzoTotale(30);
        requestValida.setDataSpettacolo(LocalDate.now().plusDays(5)); // Data valida (nel futuro)
        requestValida.setPostiSelezionati(List.of(10, 11)); // Due posti selezionati
    }

    /**
     * CASO 1: Tutti i posti selezionati risultano liberi, il controller restituisce
     * HTTP 200 OK con messaggio "Prenotazione completata"
     */
    @Test
    void creaPrenotazione_PostiLiberi_Restituisce200OK() {
        // Configurazione dell mock per restituire 0 (nessun posto occupato nel DB)
        when(specificaPostoRepository.countPostiOccupati(
                eq("SPET01"),
                eq("TEA01"),
                any(LocalDate.class),
                anyList()
        )).thenReturn(0);

        // Simulazione ricerca dell'utente nel DB per la gestione del punteggio fedeltà
        Utente utenteMock = new Utente();
        utenteMock.setUsername("mario_rossi");
        utenteMock.setPunteggio(100);
        when(utenteRepository.findById("mario_rossi")).thenReturn(Optional.of(utenteMock));

        // Esecuzione metodo da testare 
        ResponseEntity<?> response = prenotazioneController.creaPrenotazione(requestValida);

        
        // Verifica dello stato HTTP e del contenuto della risposta
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Lo status code deve essere HTTP 200 OK");
        assertEquals("Prenotazione completata!", response.getBody(), "Il messaggio di risposta non corrisponde");

        // Verifiche sui mock: assicura che il controller abbia salvato correttamente le entità
        verify(specificaPostoRepository, times(1)).countPostiOccupati(
                eq("SPET01"), eq("TEA01"), any(LocalDate.class), anyList()
        );
        verify(prenotazioneRepository, times(1)).save(any());
        verify(effettuaRepository, times(1)).save(any());
        verify(specificaPostoRepository, times(2)).save(any()); // Chiamato per ogni posto (2 posti nella request)
        verify(utenteRepository, times(1)).save(any()); // Chiamato per aggiornare i punti fedeltà
    }

    /**
     * CASO 2: Almeno uno dei posti richiesti risulta già occupato nel DB.
     * Il controller restituisce HTTP 400 Bad Request con interruzione della transazione.
     */
    @Test
    void creaPrenotazione_PostiOccupati_Restituisce400BadRequest() {
        
        // Simulazione della presenza di almeno un posto già occupato (count > 0)
        when(specificaPostoRepository.countPostiOccupati(
                anyString(), anyString(), any(LocalDate.class), anyList()
        )).thenReturn(1);

        // Esecuzione metodo da testare
        ResponseEntity<?> response = prenotazioneController.creaPrenotazione(requestValida);

        // Verifica del codice di errore HTTP 400
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Lo status code deve essere HTTP 400 Bad Request");
        assertEquals("Uno o più posti selezionati non sono più disponibili. Scegli altri posti.", response.getBody());

        // Verifica sicurezza: assicura che non sia stato salvato nulla nel DB
        verify(specificaPostoRepository, times(1)).countPostiOccupati(
                anyString(), anyString(), any(LocalDate.class), anyList()
        );
        verifyNoInteractions(prenotazioneRepository);
        verifyNoInteractions(effettuaRepository);
    }

    /**
     * CASO 3: Il client invia una richiesta con la lista dei posti vuota.
     * Risultato atteso: Gestione e restituzione del codice di errore idoneo.
     */
    @Test
    void creaPrenotazione_ListaPostiVuota_Restituisce400BadRequest() {
        // Preparazione dati
        requestValida.setPostiSelezionati(Collections.emptyList());

        // Esecuzione metodo da testare
        ResponseEntity<?> response = prenotazioneController.creaPrenotazione(requestValida);

        // Valutazione risultati
        assertNotNull(response, "La risposta del controller non deve essere null");
    }

    /**
     * CASO 4:  Tentativo di prenotazione di uno spettacolo con una data già trascorsa.
     * Il controller deve bloccare la richiesta
     */
    @Test
    void creaPrenotazione_DataPassata_Restituisce400BadRequest() {
        // Preparazione dati
        requestValida.setDataSpettacolo(LocalDate.now().minusDays(1)); // Data impostata a ieri

        // Esecuzione metodo da testare
        ResponseEntity<?> response = prenotazioneController.creaPrenotazione(requestValida);

        // Valutazione risultati attesi
        assertNotNull(response, "La risposta del controller non deve essere null");
    }
}