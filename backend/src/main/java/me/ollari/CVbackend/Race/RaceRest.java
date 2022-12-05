package me.ollari.CVbackend.Race;

import me.ollari.CVbackend.RaceFee.RaceFee;
import me.ollari.CVbackend.RaceFee.RaceFeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final RaceFeeRepository raceFeeRepository;


    /**
     * Questo costruttore viene utilizzato per inizializzare le repository che verranno utilizzate nelle chiamate del'API.
     *
     * @param raceRepository repository usata per operazioni crud inerenti alle gare
     */
    public RaceRest(RaceRepository raceRepository, RaceFeeRepository raceFeeRepository) {
        this.raceRepository = raceRepository;
        this.raceFeeRepository = raceFeeRepository;
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per ottenere una lista di tutte le gare del DB
     *
     * @return lista di oggetti gara
     */
    @GetMapping("/races")
    Iterable<Race> getRaces() {
        return raceRepository.findAll();
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per ricevere i dati di una gara in base all'id della raceFee
     * @param raceFeeId id della gara
     * @return oggetto gara
     */
    @GetMapping("/races/raceFee/{raceFeeId}")
    ResponseEntity<Race> getRaceByRaceFee(@PathVariable Long raceFeeId) {

        List<RaceFee> raceFees = raceFeeRepository.findAll();

        for (RaceFee rf : raceFees) {
            if (rf.getId().equals(raceFeeId)) {
                return new ResponseEntity<>(rf.getRacesRaceFee(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * EndPoint di tipo POST della RESP API utilizzato per creare una gara nel DB
     *
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
