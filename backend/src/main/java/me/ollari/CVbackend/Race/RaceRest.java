package me.ollari.CVbackend.Race;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Questa classe viene usata come uno degli endpoint del'API che vine usata per comunicare tra DB(tramite {@link RaceRepository}) e GUI.
 * In questa classe vengono racchiuse tutte quelle chiamate API che restituiscono oggetti o liste di oggetti inerenti alla
 * classe {@link Race}.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@RestController
public class RaceRest {

    private final RaceRepository raceRepository;


    /**
     * Questo costruttore viene utilizzato per inizializzare le repository che verranno utilizzate nelle chiamate del'API.
     * @param raceRepository repository usata per operazioni crud inerenti alle gare
     */
    public RaceRest(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per ottenere una lista di tutte le gare del DB
     * @return lista di oggetti gara
     */
    @GetMapping("/races")
    Iterable<Race> getRaces() {
        return raceRepository.findAll();
    }

    /**
     * EndPoint di tipo POST della RESP API utilizzato per creare una gara nel DB
     * @param race oggetto gara che si vuole aggiungere nel DB
     * @return 201 se creazione avvenuta con successo, 406 se avviene un problema durante l'inserimento
     */
    @PostMapping("/races")
    ResponseEntity<Race> createRace(@RequestBody Race race) {
        try {
            raceRepository.save(race);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Error e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
