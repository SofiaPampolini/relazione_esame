package org.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.example.dto.ModificaSpettacoloRequest;
import org.example.dto.SpettacoloRequest;
import org.example.dto.TeatroRequest;
import org.example.model.Notifica;
import org.example.model.Prenotazione;
import org.example.model.Rappresenta;
import org.example.model.RappresentaId;
import org.example.repository.EffettuaRepository;
import org.example.repository.NotificaRepository;
import org.example.repository.PostoRepository;
import org.example.repository.RappresentaRepository;
import org.example.repository.PrenotazioneRepository;
import org.example.repository.SpettacoloRepository;
import org.example.repository.TariffaPostoRepository;
import org.example.repository.TeatroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

/**
 * Controller Spring REST per la gestione delle funzionalità riservate agli amministratori.
 * Espone endpoint per la gestione di teatri, spettacoli, rappresentazioni e prenotazioni.
 */

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    // Dichiarazione repository necessari per l'accesso al database
    private final TeatroRepository teatroRepository;
    private final TariffaPostoRepository tariffaPostoRepository;
    private final PostoRepository postoRepository;
    private final SpettacoloRepository spettacoloRepository;
    private final RappresentaRepository rappresentaRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final EffettuaRepository effettuaRepository;
    private final NotificaRepository notificaRepository;
    
    // Costruttore della classe per l'iniezione delle dipendenze
    public AdminController(TeatroRepository teatroRepository, TariffaPostoRepository tariffaPostoRepository,
            PostoRepository postoRepository, SpettacoloRepository spettacoloRepository,
            RappresentaRepository rappresentaRepository, PrenotazioneRepository prenotazioneRepository,
            EffettuaRepository effettuaRepository, NotificaRepository notificaRepository) {
        this.teatroRepository = teatroRepository;
        this.tariffaPostoRepository = tariffaPostoRepository;
        this.postoRepository = postoRepository;
        this.spettacoloRepository = spettacoloRepository;
        this.rappresentaRepository = rappresentaRepository;
        this.prenotazioneRepository = prenotazioneRepository;
        this.effettuaRepository = effettuaRepository;
        this.notificaRepository = notificaRepository;
    }

    // Endpoint GET per la ricerca e il filtraggio degli spettacoli lato amministratore
    @GetMapping("/spettacoli")
    public ResponseEntity<?> getSpettacoliAdmin(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) String genere,
        @RequestParam(required = false) String data,
        @RequestParam(required = false) String citta){

        // Esecuzione query personalizzata nel repository con passaggio dei criteri di ricerca
        var spettacoli = rappresentaRepository.filtraSpettacoliAdmin(q, genere, data, citta);
        // Restituzione lista degli spettacoli trovati con codice HTTP 200
        return ResponseEntity.ok(spettacoli);
    }

    // Endpoint PUT per l'aggiornamento della data e dell'ora di una specifica rappresentazione
    @Transactional
    @PutMapping("/spettacolo/aggiorna")
        public ResponseEntity<?> aggiornaSpettacolo(@RequestBody ModificaSpettacoloRequest request){
            // Ricerca della rappresentazione tramite teatro, spettacolo e data attuale
            var rappOpt = rappresentaRepository.findByCodiceTeatroAndIdSpettacoloAndData(
                request.getCodiceTeatro(),
                request.getIdSpettacolo(),
                request.getVecchiaData()
            );

            // Restituzione codice HTTP 400 nel caso in cui la rappresentazione non venga trovata
            if(rappOpt.isEmpty()){
                return ResponseEntity.badRequest().body("Spettacolo non trovato");
            }

            // Estrazione entità trovata
            Rappresenta vecchiaRapp = rappOpt.get();

            // Verifica se data o ora hanno subito variazioni
            boolean dataCambiata = !request.getVecchiaData().equals(request.getNuovaData());
            boolean oraCambiata = !vecchiaRapp.getOra().equals(request.getNuovaOra());

            // Controllo disponibilità teatro in caso di modifica della data
            if(dataCambiata){
                boolean giaOccupato = rappresentaRepository.existsByCodiceTeatroAndData(
                    request.getCodiceTeatro(),
                    request.getNuovaData()
                );
                // Interruzione dell'operazione nel caso in cui il teatro sia già occupato
                if(giaOccupato){
                    return ResponseEntity.badRequest().body("Teatro già occupato per quella data");
                }
            }

            // Aggiornamento prenotazioni utenti in caso di cambiamento della data o dell'ora dello spettacolo
            if(dataCambiata || oraCambiata){
                List<Prenotazione> prenotazioni = prenotazioneRepository
                    .findByIdSpettacoloAndCodiceTeatroAndDataSpettacolo(
                        request.getIdSpettacolo(),
                        request.getCodiceTeatro(),
                        request.getVecchiaData()
                );

                // Generazione di una notifica per ogni utente associato ad una prenotazione modificata
                for(Prenotazione p : prenotazioni){
                    p.setDataSpettacolo(request.getNuovaData());
                    effettuaRepository.findByCodicePrenotazione(p.getCodicePrenotazione()).ifPresent(effettua -> {
                        Notifica n = new Notifica();
                        n.setUsername(effettua.getUsername());
                        n.setMessaggio("La tua prenotazione "+ p.getCodicePrenotazione() + 
                                       " è stata modificata: nuova data " + request.getNuovaData() + 
                                       " nuova ora " + request.getNuovaOra());
                        notificaRepository.save(n);
                    });
                }
                prenotazioneRepository.saveAll(prenotazioni);
            }

            // In caso di cambiamento della data occorre rigenerare il record poichè la data è parte della Primary Key
            if(dataCambiata){
                rappresentaRepository.delete(vecchiaRapp);

                Rappresenta nuovaRapp = new Rappresenta();
                nuovaRapp.setCodiceTeatro(request.getCodiceTeatro());
                nuovaRapp.setIdSpettacolo(request.getIdSpettacolo());
                nuovaRapp.setData(request.getNuovaData());
                nuovaRapp.setOra(request.getNuovaOra());
                nuovaRapp.setStato(vecchiaRapp.getStato());

                rappresentaRepository.save(nuovaRapp);
            } else {
                // Se la data è invariata si aggiorna semplicemente l'ora
                vecchiaRapp.setOra(request.getNuovaOra());
                rappresentaRepository.save(vecchiaRapp);
            }

            return ResponseEntity.ok("Spettacolo e prenotazioni associate aggiornati correttamente");
    }
    
    // Endpoint POST per la creazione di un nuovo teatro e dei relativi posti/tariffe
    @Transactional
    @PostMapping("/teatro")
    public ResponseEntity<String> creaTeatro(@RequestBody TeatroRequest request){
        String nome = request.getTeatro().getNome();
        String citta = request.getTeatro().getCitta();

        // Controllo esistenza duplicati
        if(teatroRepository.existsByNomeIgnoreCaseAndCittaIgnoreCase(nome, citta)){
            return ResponseEntity.badRequest().body("Teatro già presente.");
        }

        // Generazione nuovo codice sequenziale se il teatro non è presente
        String ultimoCodice = teatroRepository.findMaxCodiceTeatro();
        String nuovoCodice = (ultimoCodice == null || ultimoCodice.isEmpty())
            ? "TE000001"
            : String.format("TE%06d", Integer.parseInt(ultimoCodice.replace("TE", "")) + 1);

        request.getTeatro().setCodiceTeatro(nuovoCodice);
        teatroRepository.save(request.getTeatro());

        // Se sono presenti tariffe associate, imposta il codice teatro e salva
        if(request.getTariffe() != null){
            request.getTariffe().forEach(t -> t.setCodiceTeatro(nuovoCodice));
            tariffaPostoRepository.saveAll(request.getTariffe());
        }

        // Se sono prenenti posti associati, imposta il codice teatro e salva
        if(request.getPosti() != null){
            request.getPosti().forEach(p -> p.setCodiceTeatro(nuovoCodice));
            postoRepository.saveAll(request.getPosti());
        }

        return ResponseEntity.ok("Teatro creato con successo! Codice: " + nuovoCodice);
    }

    // Endpoint POST per la registrazione di un nuovo spettacolo o di una nuova replica di uno spettacolo esistente
    @Transactional
    @PostMapping("/spettacolo")
    public ResponseEntity<String> creaSpettacolo(@RequestBody SpettacoloRequest request){
        Rappresenta rapp = request.getRappresentazione();
        
        // Verifica disponibilità teatro per la data selezionata
        boolean giaOccupato = rappresentaRepository.existsByCodiceTeatroAndData(
            rapp.getCodiceTeatro(),
            rapp.getData()
        );
        if(giaOccupato){
            return ResponseEntity.badRequest().body("Teatro già occupato per quella data");
        }

        String nomeSpettacolo = request.getSpettacolo().getNome();
       
       // Ricerca dello spettacolo specificato tramite nome 
        var spettacoloEsistenteOpt = spettacoloRepository.findByNomeIgnoreCase(nomeSpettacolo);

        if(spettacoloEsistenteOpt.isPresent()){
            // Spettacolo già esistente. Collegamento della nuova replica all'ID dello spettacolo
            String idEsistente = spettacoloEsistenteOpt.get().getId();
            rapp.setIdSpettacolo(idEsistente);
            rappresentaRepository.save(rapp);

            return ResponseEntity.ok("Nuova data aggiunta allo spettacolo " + nomeSpettacolo);
        } else {
            // Spettacolo non presente. Generazione di un nuovo ID sequenziale e salvataggio nelle tabelle spettacolo e rappresenta del database
            String ultimoId = spettacoloRepository.findMaxIdSpettacolo();
            String nuovoId = (ultimoId == null || ultimoId.isEmpty())
                ? "SPET0001"
                : String.format("SPET%04d", Integer.parseInt(ultimoId.replace("SPET", "")) + 1);

            request.getSpettacolo().setId(nuovoId);
            spettacoloRepository.save(request.getSpettacolo());

            request.getRappresentazione().setIdSpettacolo(nuovoId);
            rappresentaRepository.save(request.getRappresentazione());

            return ResponseEntity.ok("Nuovo spettacolo e replica creati con successo!");
        }
    }

    // Endpoint DELETE per annullare/eliminare una specifica rappresentazione
    @Transactional
    @DeleteMapping("/spettacolo")
    public ResponseEntity<?> eliminaRappresentazione(
        @RequestParam String idSpettacolo,
        @RequestParam String codiceTeatro,
        @RequestParam LocalDate dataSpettacolo){
            // Ricerca delle prenotazioni effettuate dagli utenti per la data specificata
            List<Prenotazione> prenotazioni = prenotazioneRepository
            .findByIdSpettacoloAndCodiceTeatroAndDataSpettacolo(idSpettacolo, codiceTeatro, dataSpettacolo);

            // Per ogni prenotazione trovata si aggiorna lo stato in "Cancellata"
            for(Prenotazione p: prenotazioni){
                p.setStato("Cancellata");
            }
            
            prenotazioneRepository.saveAll(prenotazioni);

            // Estrazione della rappresentazione e aggiornamento dello stato in "Annullato"
            RappresentaId id = new RappresentaId(codiceTeatro, idSpettacolo, dataSpettacolo);
            Rappresenta rappresenta = rappresentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rappresentazione non Trovata"));

            rappresenta.setStato("Annullato");
            rappresentaRepository.save(rappresenta);

            return ResponseEntity.ok("Spettacolo eliminato");
        }
}
