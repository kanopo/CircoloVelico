package me.ollari.CVbackend.ParkingFee;

import me.ollari.CVbackend.Boat.Boat;
import me.ollari.CVbackend.Boat.BoatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe viene usata come uno degli endpoint del'API che vine usata per comunicare tra DB(tramite {@link ParkingFeeRepository}) e GUI.
 * In questa classe vengono racchiuse tutte quelle chiamate API che restituiscono oggetti o liste di oggetti inerenti alla
 * classe {@link ParkingFee}.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@RestController
public class ParkingFeeRest {

    private final ParkingFeeRepository parkingFeeRepository;
    private final BoatRepository boatRepository;

    /**
     * Questo costruttore viene utilizzato per inizializzare le repository che verranno utilizzate nelle chiamate del'API.
     * @param parkingFeeRepository repository usata per operazioni crud inerenti alle tasse di rimessaggio
     * @param boatRepository repository usata per operazioni crud inerenti alle barche
     */
    public ParkingFeeRest(ParkingFeeRepository parkingFeeRepository, BoatRepository boatRepository) {
        this.parkingFeeRepository = parkingFeeRepository;
        this.boatRepository = boatRepository;
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per ottenere una lista di tutte le tasse di parcheggio salvate nel DB
     * @return lista di tasse di parcheggio
     */
    @GetMapping("/parkingFees")
    Iterable<ParkingFee> getParkingFees() {
        return parkingFeeRepository.findAll();
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per ricevere le tasse di parcheggio in base all'id dell'utente
     * @param memberId id dell'utente
     * @return lista di tasse di parcheggio
     */
    @GetMapping("/parkingFees/memberId/{memberId}")
    ResponseEntity<Iterable<ParkingFee>> getParkingFeesByMemberId(@PathVariable Long memberId) {
        List<ParkingFee> parkingFees = parkingFeeRepository.findAll();
        List<ParkingFee> memberParkingFees = new ArrayList<>();


        if (parkingFees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (ParkingFee pf : parkingFees) {
            if (pf.getMembersParkingFees().getId().equals(memberId)) {
                memberParkingFees.add(pf);
            }
        }

        if (memberParkingFees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(memberParkingFees, HttpStatus.OK);
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per ottenere la lista delle tasse di parcheggio relative al'id di una barca
     * @param boatId id della barca
     * @return lista di tasse di parcheggio
     */
    @GetMapping("/parkingFees/boatId/{boatId}")
    ResponseEntity<Iterable<ParkingFee>> getParkingFeeByBoatId(@PathVariable Long boatId) {
        List<ParkingFee> parkingFees = parkingFeeRepository.findAll();
        List<ParkingFee> boatsParkingFees = new ArrayList<>();

        if (parkingFees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (ParkingFee pf : parkingFees) {
            if (pf.getBoatsParkingFee().getId().equals(boatId)) {
                boatsParkingFees.add(pf);
            }
        }

        if (boatsParkingFees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(boatsParkingFees, HttpStatus.OK);


    }


    /**
     * EndPoint di tipo PUT della RESP API utilizzato per aggiungere una tassa di parcheggio alla barca
     * @param boatId id della barca
     * @param parkingFee oggetto contenente i dati della tassa di partcheggio
     * @return 404 se l'id della barca non esiste, 406 se ci sono problemi d'inserimento della tassa nel DB, 200 se l'inserimento avviene correttamente
     */
    @PutMapping("/parkingFees/boatId/{boatId}")
    ResponseEntity<ParkingFee> createParkingFeeForBoat(@PathVariable Long boatId, @RequestBody ParkingFee parkingFee) {
        Boat boat = boatRepository.findById(boatId).orElse(null);

        if (boat != null) {
            parkingFee.setBoatsParkingFee(boat);
            parkingFee.setMembersParkingFees(boat.getMembersBoat());

            try {
                parkingFeeRepository.save(parkingFee);
            } catch (Error e) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
