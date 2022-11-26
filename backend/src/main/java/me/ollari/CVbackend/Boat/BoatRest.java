package me.ollari.CVbackend.Boat;

import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import me.ollari.CVbackend.ParkingFee.ParkingFee;
import me.ollari.CVbackend.ParkingFee.ParkingFeeRepository;
import me.ollari.CVbackend.Race.Race;
import me.ollari.CVbackend.Race.RaceRepository;
import me.ollari.CVbackend.RaceFee.RaceFee;
import me.ollari.CVbackend.RaceFee.RaceFeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


/**
 * Questa classe viene usata come uno degli endpoint del'API che vine usata per comunicare tra DB(tramite {@link BoatRepository}) e GUI.
 * In questa classe vengono racchiuse tutte quelle chiamate API che restituiscono oggetti o liste di oggetti inerenti alla
 * classe {@link Boat}.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@RestController
public class BoatRest {


    private final BoatRepository boatRepository;
    private final MemberRepository memberRepository;
    private final RaceFeeRepository raceFeeRepository;
    private final ParkingFeeRepository parkingFeeRepository;
    private final RaceRepository raceRepository;

    /**
     * Questo costruttore viene utilizzato per inizializzare le repository che verranno utilizzate nelle chiamate del'API.
     *
     * @param boatRepository       repository usata per operazioni crud inerenti alle barche
     * @param memberRepository     repository usata per operazioni crud inerenti ai membri
     * @param raceFeeRepository    repository usata per operazioni crud inerenti alle tasse delle gare
     * @param parkingFeeRepository repository usata per operazioni crud inerenti alle tasse di rimessaggio
     * @param raceRepository       repository usata per operazioni crud inerenti alle gare
     */
    public BoatRest(BoatRepository boatRepository, MemberRepository memberRepository, RaceFeeRepository raceFeeRepository, ParkingFeeRepository parkingFeeRepository, RaceRepository raceRepository) {
        this.boatRepository = boatRepository;
        this.memberRepository = memberRepository;
        this.raceFeeRepository = raceFeeRepository;
        this.parkingFeeRepository = parkingFeeRepository;
        this.raceRepository = raceRepository;
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce tutte le barche
     *
     * @return lista di tutte le barche
     */
    @GetMapping("/boats")
    Iterable<Boat> getBoats() {
        return boatRepository.findAll();
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce la barca con il corrispettivo id
     *
     * @param boatId id della barca
     * @return la barca richiesta e il codice 200 oppure se la barca con l'id scelte non dovesse
     * esistere, 404(not found)
     */
    @GetMapping("/boats/{boatId}")
    ResponseEntity<Boat> getBoatById(@PathVariable Long boatId) {
        Boat boat = boatRepository.findById(boatId).orElse(null);

        if (boat != null) {
            return new ResponseEntity<>(boat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce tutte le barche associate a un'utente
     *
     * @param memberId id dell'utente
     * @return tutte le barche dell'utente e il codice 200, altrimenti, se l'utente non esiste, restituisce il codice 404(not found)
     */
    @GetMapping("/boats/memberId/{memberId}")
    ResponseEntity<Iterable<Boat>> getBoatsByUserId(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            return new ResponseEntity<>(member.getBoats(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce tutte le barche che hanno la tassa di parcheggio scaduta
     *
     * @param memberId id dell'utente
     * @return lista di barche con tassa scaduta e il codice 200, se l'id del membro non esiste, restituisce 404(not found)
     */
    @GetMapping("/boats/expiredParking/{memberId}")
    ResponseEntity<Iterable<Boat>> getBoatsOfUserWithExpiredParking(@PathVariable Long memberId) {
        if (memberRepository.existsById(memberId)) {
            Member member = memberRepository.findById(memberId).orElseThrow();

            LocalDate today = LocalDate.now();

            Set<Boat> boats = member.getBoats();

            Set<Boat> expiredBoats = new HashSet<>();

            for (Boat b : boats) {

                if (b.getParkingFees().isEmpty()) {
                    expiredBoats.add(b);
                    break;
                }

                boolean validParking = false;


                for (ParkingFee pf : b.getParkingFees()) {
                    LocalDate start = pf.getStart();
                    LocalDate end = pf.getEnd();

                    if ((start.isBefore(today) || start.isEqual(today)) && (end.isAfter(today) || end.isEqual(today))) {
                        //boats.remove(b);
                        validParking = true;
                        break;
                    }
                }

                if (!validParking) {
                    expiredBoats.add(b);
                    break;
                }
            }


            return new ResponseEntity<>(expiredBoats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce la barca associata a una tassa di parcheggio
     *
     * @param parkingFeeId id della tassa di parcheggio
     * @return la barca associata e il codice 200, altrimenti se non dovesse esistere l'id della tassa, restituisce 404
     */
    @GetMapping("/boats/parkingFeeId/{parkingFeeId}")
    ResponseEntity<Boat> getBoatByParkingFeeId(@PathVariable Long parkingFeeId) {
        ParkingFee pf = parkingFeeRepository.findById(parkingFeeId).orElse(null);

        if (pf == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(pf.getBoatsParkingFee(), HttpStatus.OK);
        }
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce tutte le barche di un'utente non iscritte a una gara
     *
     * @param memberId id dell'utente
     * @param raceId   id della gara
     * @return tutte le barche dell'utente non iscritte alla gara, se la gara o l'utente non dovessero esistere in DB, viene restituito 404
     */
    @GetMapping("/boats/memberId/{memberId}/notInRace/{raceId}")
    ResponseEntity<Iterable<Boat>> boatOfMemberNotSubscribedToRace(@PathVariable Long memberId, @PathVariable Long raceId) {
        Member m = memberRepository.findById(memberId).orElse(null);
        Race r = raceRepository.findById(memberId).orElse(null);


        Set<Boat> boatsNotSubscribed = new HashSet<>();

        if (r != null && m != null) {
            Set<Boat> membersBoats = m.getBoats();

            for (Boat b : membersBoats) {
                if (b.getRaceFees().isEmpty()) {
                    // non iscritto a nessuna gara -> può iscriversi
                   boatsNotSubscribed.add(b);
                } else {
                    // devo fare il check per vesere se la barca b ha gia una tassa che fa riferimento alla gare di id raceId
                    for (RaceFee rf : b.getRaceFees()) {
                        if (rf.getRacesRaceFee().getId().equals(raceId)) {
                            // barca già iscritta alla gara, interrompere il loop e skippare la barca
                            break;
                        }

                        boatsNotSubscribed.add(b);
                    }
                }
            }
            return new ResponseEntity<>(boatsNotSubscribed, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * EndPoint di tipo PUT della RESP API utilizzato per aggiungere un'imbarcazione a un membro
     *
     * @param boat     oggetto che rappresenta la barca con le sue feature {@link Boat}
     * @param memberId id dell'utente
     * @return 406 se l'utente non esiste o se l'inserimento della barca fallisce, 201 se la barca viene inserita correttamente.
     */
    @PutMapping("/boats/memberId/{memberId}")
    ResponseEntity<Boat> createBoat(@RequestBody Boat boat, @PathVariable Long memberId) {

        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            boat.setMembersBoat(member);

            try {
                boatRepository.save(boat);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }


    }

    /**
     * EndPoint di tipo DELETE della RESP API utilizzato per eliminare una barca
     *
     * @param boatId id della barca da eliminare
     * @return 200 se la barca esiste ed è stata eliminata, 404 se la barca non esiste o se l'eliminazione è fallita
     */
    @DeleteMapping("/boats/{boatId}")
    ResponseEntity<Boat> deleteBoat(@PathVariable Long boatId) {
        if (boatRepository.existsById(boatId)) {
            try {
                boatRepository.deleteById(boatId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Error e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }


}
