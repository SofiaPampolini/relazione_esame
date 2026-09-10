package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.controller.AdminController;
import org.example.controller.PrenotazioneController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test d'integrazione globale dell'applicazione Spring Boot.
 * Avvio dell'intero contesto Spring per verificare che tutti i componenti, 
 * le configurazioni e i dipendenze siano collegati correttamente.
 */
@SpringBootTest
class AppTest {

    @Autowired
    private AdminController adminController;

    @Autowired
    private PrenotazioneController prenotazioneController;

    /**
     * Verfica che il contesto di Spring si carichi correttamente e che 
     * i controller siano stati creati e iniettati nello Spring Container.
     */
    @Test
    void contextLoads() {
        // Se il contesto non riesce a partire (es. errore nei Repository o entità JPA), il test fallisce subito.
        assertNotNull(adminController, "L'AdminController deve essere caricato nel contesto Spring");
        assertNotNull(prenotazioneController, "Il PrenotazioneController deve essere caricato nel contesto Spring");
    }
}