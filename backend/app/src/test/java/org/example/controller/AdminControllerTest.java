package org.example.controller;

import org.example.dto.ModificaSpettacoloRequest;
import org.example.model.Effettua;
import org.example.model.Notifica;
import org.example.model.Prenotazione;
import org.example.model.Rappresenta;
import org.example.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe di test unitario per AdminController.
 * Utilizza MockitoExtension per isolare il controller dal database reale,
 * simulando il comportamento dei Repository (Mocking).
 */
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    // Dipendenze fittizie (Mock) simulate per evitare chiamate al database
    @Mock
    private RappresentaRepository rappresentaRepository;

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private EffettuaRepository effettuaRepository;

    @Mock
    private NotificaRepository notificaRepository;

    // Inietta automaticamente i Mock all'interno dell'istanza di AdminController
    @InjectMocks
    private AdminController adminController;

    /**
     * CASO 1: Verifica che in caso lo spettacolo da modificare non sia presente nel
     * DB, il controller restituisce un errore HTTP 400 (Bad Request).
     */
    @Test
    void aggiornaSpettacolo_SpettacoloNonTrovato_RestituisceErrore() {
        // Creazione richiesta
        ModificaSpettacoloRequest request = creaRequestBase();

        // Simulazione della mancata restituzione della rappresentazione dal DB
        when(rappresentaRepository.findByCodiceTeatroAndIdSpettacoloAndData(any(), any(), any()))
                .thenReturn(Optional.empty());

        // Chiamata al metodo del controller da testare
        ResponseEntity<?> risposta = adminController.aggiornaSpettacolo(request);

        // Controllo dello stato HTTP e del messaggio di errore restituito
        assertEquals(HttpStatus.BAD_REQUEST, risposta.getStatusCode());
        assertEquals("Spettacolo non trovato", risposta.getBody());
    }

    /**
     * CASO 2: Verifica che in caso l'amministratore tenti di spostare lo spettacolo in una 
     * data in cui il teatro è già occupato, il sistema blocchi l'operazione con errore HTTP 400.
     */
    @Test
    void aggiornaSpettacolo_TeatroOccupato_RestituisceErrore() {
        // Preparazione dati
        ModificaSpettacoloRequest request = creaRequestBase();
        Rappresenta vecchiaRapp = new Rappresenta();
        vecchiaRapp.setOra(LocalTime.of(20, 0));

        // Simulazione esistenza spettacolo 
        when(rappresentaRepository.findByCodiceTeatroAndIdSpettacoloAndData(any(), any(), any()))
                .thenReturn(Optional.of(vecchiaRapp));

        // Per la data impostata il teatro risulta già occupato
        when(rappresentaRepository.existsByCodiceTeatroAndData("TE000001", LocalDate.of(2026, 10, 15)))
                .thenReturn(true);

        // Chiamata metodo del controller
        ResponseEntity<?> risposta = adminController.aggiornaSpettacolo(request);

        // Verifica risultati attesi
        assertEquals(HttpStatus.BAD_REQUEST, risposta.getStatusCode());
        assertEquals("Teatro già occupato per quella data", risposta.getBody());
    }

    /**
     * CASO 3: Verifica del flusso completo di successo con cambio data e orario.
     * Ri-creazione della rappresentazione, aggiornamento delle prenotazioni esistenti 
     * e invio delle relative notifiche agli utenti coinvolti.
     */
    @Test
    void aggiornaSpettacolo_CambioDataEOra_SuccessoConNotifiche() {
        // Preparazione dei dati del test
        ModificaSpettacoloRequest request = creaRequestBase();
        
        Rappresenta vecchiaRapp = new Rappresenta();
        vecchiaRapp.setCodiceTeatro("TE000001");
        vecchiaRapp.setIdSpettacolo("SPET0001");
        vecchiaRapp.setData(LocalDate.of(2026, 10, 10));
        vecchiaRapp.setOra(LocalTime.of(20, 0));
        vecchiaRapp.setStato("Programmato");

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setCodicePrenotazione("PR123456");

        Effettua effettua = new Effettua();
        effettua.setUsername("mario_rossi");

        // Configurazione delle risposte dei Mock
        when(rappresentaRepository.findByCodiceTeatroAndIdSpettacoloAndData(any(), any(), any()))
                .thenReturn(Optional.of(vecchiaRapp));
        
        // Il teatro risulta libero per la nuova data
        when(rappresentaRepository.existsByCodiceTeatroAndData(any(), any()))
                .thenReturn(false);

        // Esiste una prenotazione associata alla vecchia data
        when(prenotazioneRepository.findByIdSpettacoloAndCodiceTeatroAndDataSpettacolo(any(), any(), any()))
                .thenReturn(List.of(prenotazione));

        // Ricerca dell'utente che ha effettuato la prenotazione per inviargli la notifica
        when(effettuaRepository.findByCodicePrenotazione("PR123456"))
                .thenReturn(Optional.of(effettua));

        // Esecuzione dell'operazione
        ResponseEntity<?> risposta = adminController.aggiornaSpettacolo(request);

        // Verifica dei risultati e degli effetti collaterali
        assertEquals(HttpStatus.OK, risposta.getStatusCode());
        assertEquals("Spettacolo e prenotazioni associate aggiornati correttamente", risposta.getBody());

        // Verifiche sui metodi eseguiti dai Repository (Effetti collaterali attesi)
        verify(rappresentaRepository).delete(vecchiaRapp);                       // La vecchia chiave primaria deve essere cancellata
        verify(rappresentaRepository).save(any(Rappresenta.class));        // Deve essere inserita la nuova rappresentazione
        verify(prenotazioneRepository).saveAll(anyList());                       // Le prenotazioni devono essere aggiornate
        verify(notificaRepository).save(any(Notifica.class));              // La notifica all'utente deve essere salvata nel DB
    }

    /**
     * Metodo helper privato per costruire un oggetto DTO di richiesta standard da riutilizzare nei test.
     */
    private ModificaSpettacoloRequest creaRequestBase() {
        ModificaSpettacoloRequest request = new ModificaSpettacoloRequest();
        request.setCodiceTeatro("TE000001");
        request.setIdSpettacolo("SPET0001");
        request.setVecchiaData(LocalDate.of(2026, 10, 10));
        request.setNuovaData(LocalDate.of(2026, 10, 15));
        request.setNuovaOra(LocalTime.of(21, 0));
        return request;
    }
}