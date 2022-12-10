package me.ollari.CVbackend.Race;

import me.ollari.CVbackend.Boat.Boat;
import me.ollari.CVbackend.Boat.BoatRepository;
import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import me.ollari.CVbackend.RaceFee.RaceFee;
import me.ollari.CVbackend.RaceFee.RaceFeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class RaceTest {

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    RaceFeeRepository raceFeeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoatRepository boatRepository;

    @Autowired
    RaceRest raceRest;

    @BeforeEach
    void setUp() {

        if (raceFeeRepository.findAll().size() > 0) {
            raceFeeRepository.deleteAll();
        }

        if (raceRepository.findAll().size() > 0) {
            raceRepository.deleteAll();
        }

        if (boatRepository.findAll().size() > 0) {
            boatRepository.deleteAll();
        }

        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
        }



    }

    @AfterEach
    void tearDown() {
        if (raceRepository.findAll().size() > 0) {
            raceRepository.deleteAll();
        }

        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
        }

        if (boatRepository.findAll().size() > 0) {
            boatRepository.deleteAll();
        }

        if (raceFeeRepository.findAll().size() > 0) {
            raceFeeRepository.deleteAll();
        }
    }

    @Test
    @DisplayName("Adding race with null date")
    void addRaceWithNullDate() {
        Race race = new Race();
        race.setPrice(1.1);
        race.setName("race");
        race.setAward(100.1);

        assertThrows(RuntimeException.class, () -> {
            raceRepository.save(race);
        });
    }

    @Test
    @DisplayName("Adding race with null price")
    void addRaceWithNullPrice() {
        Race race = new Race();
        race.setDate(LocalDate.now().plusWeeks(12));
        race.setName("race");
        race.setAward(100.1);

        assertThrows(RuntimeException.class, () -> {
            raceRepository.save(race);
        });
    }

    @Test
    @DisplayName("Adding race with null name")
    void addRaceWithNullName() {
        Race race = new Race();
        race.setDate(LocalDate.now().plusWeeks(12));
        race.setPrice(1.1);
        race.setAward(100.1);

        assertThrows(RuntimeException.class, () -> {
            raceRepository.save(race);
        });
    }

    @Test
    @DisplayName("Adding race with null award")
    void addRaceWithNullAward() {
        Race race = new Race();
        race.setDate(LocalDate.now().plusWeeks(12));
        race.setPrice(1.1);
        race.setName("race");

        assertThrows(RuntimeException.class, () -> {
            raceRepository.save(race);
        });
    }

    @Test
    @DisplayName("Get races using rest")
    void getRacesUsingRest() {
        Race race = new Race(LocalDate.now().plusWeeks(2), 1.1, "gara", 100.7);

        raceRepository.save(race);

        Iterable<Race> iterableResponseEntity = raceRest.getRaces();

        List<Race> races = (List<Race>) iterableResponseEntity;

        assertEquals(1, races.size());
    }

    @Test
    @DisplayName("Removing race should remove raceFee but not remove, boat and member")
    void removingRace() {

    }


    @Test
    @Disabled
    @DisplayName("Removing race fee should leave race, member and boat in the db")
    void removingRaceFee() {

    }

    @Test
    @DisplayName("Get race by raceFeeId")
    void getRaceByIdUsingRest() {


        Member m1 = new Member();

        m1.setUsername("zxc");
        m1.setFiscalCode("zxc");
        m1.setPassword("zxc");
        m1.setAddress("zxc");
        m1.setName("zxc");
        m1.setSurname("zxc");

        memberRepository.save(m1);

        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(12.3);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        Race race1 = new Race(LocalDate.now().plusWeeks(1), 1.1, "gara1", 100.7);
        Race race2 = new Race(LocalDate.now().plusWeeks(2), 1.1, "gara2", 100.7);
        Race race3 = new Race(LocalDate.now().plusWeeks(3), 1.1, "gara3", 100.7);

        raceRepository.save(race1);
        raceRepository.save(race2);
        raceRepository.save(race3);


        RaceFee raceFee = new RaceFee();

        raceFee.setBoatsRaceFee(b1);
        raceFee.setRacesRaceFee(race2);
        raceFee.setPrice(race2.getPrice());
        raceFee.setMembersRaceFee(m1);
        raceFee.setPaymentDate(LocalDate.now().minusDays(3));

        raceFeeRepository.save(raceFee);

        Long raceFeeId = raceFeeRepository.findAll().get(0).getId();


        ResponseEntity<Race> responseEntity1 = raceRest.getRaceByRaceFee(raceFeeId);


        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
        assertEquals("gara2", responseEntity1.getBody().getName());

        ResponseEntity<Race> responseEntity2 = raceRest.getRaceByRaceFee(raceFeeId + 1);


        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());


    }

    @Test
    @DisplayName("Post race using rest")
    void postRaceUsingRest() {
        Race race1 = new Race(LocalDate.now().plusWeeks(2), 1.1, "gara", 100.7);
        Race race2 = new Race();

        race2.setAward(11.2);
        race2.setPrice(11.2);
        // missing date and name

        ResponseEntity<Race> responseEntity1 = raceRest.createRace(race1);

        assertThrows(RuntimeException.class, () -> {
            ResponseEntity<Race> responseEntity2 = raceRest.createRace(race2);
        });


        List<Race> races = raceRepository.findAll();

        Race r = races.get(0);

        assertEquals(LocalDate.now().plusWeeks(2), r.getDate());
        assertEquals(1.1, r.getPrice(), 0.001);
        assertEquals(100.7, r.getAward(), 0.001);
        assertEquals("gara", r.getName());
    }


}