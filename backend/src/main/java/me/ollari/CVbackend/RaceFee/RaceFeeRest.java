package me.ollari.CVbackend.RaceFee;

import me.ollari.CVbackend.Boat.Boat;
import me.ollari.CVbackend.Boat.BoatRepository;
import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import me.ollari.CVbackend.Race.Race;
import me.ollari.CVbackend.Race.RaceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Questa classe viene usata come uno degli endpoint del'API che vine usata per comunicare tra DB(tramite {@link RaceFeeRepository}) e GUI.
 * In questa classe vengono racchiuse tutte quelle chiamate API che restituiscono oggetti o liste di oggetti inerenti alla
 * classe {@link RaceFee}.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@RestController
public class RaceFeeRest {

    private final RaceFeeRepository raceFeeRepository;
    private final BoatRepository boatRepository;
    private final RaceRepository raceRepository;
    private final MemberRepository memberRepository;

    /**
     * Questo costruttore viene utilizzato per inizializzare le repository che verranno utilizzate nelle chiamate del'API.
     *
     * @param raceFeeRepository repository usata per operazioni crud inerenti alle tasse delle gare
     * @param boatRepository    repository usata per operazioni crud inerenti alle barche
     * @param raceRepository    repository usata per operazioni crud inerenti alle gare
     */
    public RaceFeeRest(RaceFeeRepository raceFeeRepository, BoatRepository boatRepository, RaceRepository raceRepository, MemberRepository memberRepository) {
        this.raceFeeRepository = raceFeeRepository;
        this.boatRepository = boatRepository;
        this.raceRepository = raceRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per richiedere tutte le tasse di partecipazione alle gare salvate nel DB
     *
     * @return lista dui oggetti raceFee
     */
    @GetMapping("/raceFees")
    Iterable<RaceFee> getRaceFees() {
        return raceFeeRepository.findAll();
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce la raceFess con il corrispettivo id
     * @param raceId id della gara
     * @return dati della raceFee
     */
    @GetMapping("/raceFees/{raceId}")
    ResponseEntity<RaceFee> getRaceFeeById(@PathVariable Long raceId) {


        RaceFee raceFee = raceFeeRepository.findById(raceId).orElse(null);

        if (raceFee != null) {
            return new ResponseEntity<>(raceFee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/raceFees/memberId/{memberId}")
    ResponseEntity<Iterable<RaceFee>> getRaceFeesByMemberId(@PathVariable Long memberId) {

        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            return new ResponseEntity<>(member.getRaceFees(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }



    /**
     * EndPoint di tipo POST della RESP API utilizzato per creare una tassa d'iscrizione a una gara
     *
     * @param boatId id della barca
     * @param raceId id della gara
     * @return 201 se la tassa viene creata e 406 se la creazione fallisce
     */
    @PostMapping("/raceFees/raceId/{raceId}/boatId/{boatId}")
    ResponseEntity<RaceFee> createRaceFee(@PathVariable Long boatId, @PathVariable Long raceId) {

        Boat b = boatRepository.findById(boatId).orElse(null);
        Race r = raceRepository.findById(raceId).orElse(null);

        if (b != null && r != null) {
            Member m = b.getMembersBoat();

            // all objects exists
            /*
            TODO:
                - check if the boat is already in the race fees list (association between race id, race boat)
             */

            if (b.getMembersBoat().getId().equals(m.getId())) {
                LocalDate today = LocalDate.now();
                RaceFee raceFee = new RaceFee();

                raceFee.setRacesRaceFee(r);
                raceFee.setBoatsRaceFee(b);
                raceFee.setMembersRaceFee(m);
                raceFee.setPrice(r.getPrice());
                raceFee.setPaymentDate(today);

                // boat belong to the correct member
                List<RaceFee> raceFees = raceFeeRepository.findAll();
                if (raceFees.isEmpty()) {
                    // boat can be registered to the race
                    raceFeeRepository.save(raceFee);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } else {


                    for (RaceFee rf : raceFees) {
                        if (rf.getBoatsRaceFee().getId().equals(boatId) && rf.getRacesRaceFee().getId().equals(raceId)) {
                            // boat id and race id is present in the same race fee, -> the boat is already signed in

                            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                        } else {

                            raceFeeRepository.save(raceFee);
                            return new ResponseEntity<>(HttpStatus.CREATED);

                        }
                    }
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
