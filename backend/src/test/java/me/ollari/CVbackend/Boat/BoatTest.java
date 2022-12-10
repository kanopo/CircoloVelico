package me.ollari.CVbackend.Boat;

import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import me.ollari.CVbackend.Race.Race;
import me.ollari.CVbackend.Race.RaceRepository;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class BoatTest {

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private RaceFeeRepository raceFeeRepository;

    @Autowired
    private BoatRest boatRest;

    @BeforeEach
    void setUp() {
        if (boatRepository.findAll().size() > 0) {
            boatRepository.deleteAll();
        }

        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
        }

        if (raceRepository.findAll().size() > 0) {
            raceRepository.deleteAll();
        }

        if (raceFeeRepository.findAll().size() > 0) {
            raceFeeRepository.deleteAll();
        }
    }

    @AfterEach
    void tearDown() {
        if (boatRepository.findAll().size() > 0) {
            boatRepository.deleteAll();
        }

        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
        }

        if (raceRepository.findAll().size() > 0) {
            raceRepository.deleteAll();
        }

        if (raceFeeRepository.findAll().size() > 0) {
            raceFeeRepository.deleteAll();
        }

    }


    @Test
    @DisplayName("Boat should have a reference to an existing member")
    void boatShouldHaveExistingMember() {

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");


        Boat boat = new Boat();

        // questo test lancia un errore perche' l'utente non e' registrato nel db
        // manca il memberRepository.save(m1);

        boat.setName("Barca bella");
        boat.setMembersBoat(m1);

        assertThrows(RuntimeException.class, () -> boatRepository.save(boat));
    }

    @Test
    @DisplayName("Boat should have length")
    void boatShouldHaveLength() {

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);


        Boat boat = new Boat();


        boat.setName("Barca bella");
        boat.setMembersBoat(m1);

        assertThrows(RuntimeException.class, () -> boatRepository.save(boat));
    }

    @Test
    @DisplayName("Boat should have a name")
    void boatShouldHaveName() {

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);


        Boat boat = new Boat();


        boat.setLength(1.1);
        boat.setMembersBoat(m1);

        assertThrows(RuntimeException.class, () -> boatRepository.save(boat));
    }

    @Test
    @DisplayName("Boat should have member")
    void boatShouldHaveMember() {

        Boat boat = new Boat();


        boat.setName("Barca bella");
        boat.setLength(1.1);

        assertThrows(RuntimeException.class, () -> boatRepository.save(boat));
    }

    @Test
    @DisplayName("Removing boat from db should leave the member")
    void removingBoatFromDBShouldLeaveMember() {

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        Boat b1 = new Boat();


        b1.setLength(1.1);
        b1.setMembersBoat(m1);
        b1.setName("blu");

        boatRepository.save(b1);

        boatRepository.deleteAll();

        assertEquals(1, memberRepository.findAll().size());

    }

    @Test
    @DisplayName("Removing member from db should remove all of his boats")
    void removingMemberShouldRemoveAllOfHisBoats() {

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        Boat b1 = new Boat();


        b1.setLength(1.1);
        b1.setMembersBoat(m1);
        b1.setName("blu");

        boatRepository.save(b1);

        memberRepository.deleteAll();

        assertEquals(0, boatRepository.findAll().size());

    }

    @Test
    @DisplayName("Get all boats using rest")
    void getAllBoatsUsingRest() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        Boat b1 = new Boat();


        b1.setLength(1.1);
        b1.setMembersBoat(m1);
        b1.setName("blu");

        boatRepository.save(b1);

        Boat b2 = new Boat();


        b2.setLength(1.1);
        b2.setMembersBoat(m1);
        b2.setName("blu");

        boatRepository.save(b2);

        List<Boat> boats = (List<Boat>) boatRest.getBoats();

        assertEquals(2, boats.size());
    }

    @Test
    @DisplayName("Get boat by Id using rest")
    void getBoatById() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        Boat b1 = new Boat();


        b1.setLength(1.1);
        b1.setMembersBoat(m1);
        b1.setName("blu");

        boatRepository.save(b1);

        Boat b2 = new Boat();


        b2.setLength(1.1);
        b2.setMembersBoat(m1);
        b2.setName("rosso");

        boatRepository.save(b2);

        Long boatId = boatRepository.findAll().get(0).getId();

        ResponseEntity<Boat> boat1 = boatRest.getBoatById(boatId);
        ResponseEntity<Boat> boat2 = boatRest.getBoatById(boatId + 2);

        assertEquals(HttpStatus.OK, boat1.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, boat2.getStatusCode());
        assertEquals("blu", boat1.getBody().getName());

    }

    @Test
    @DisplayName("Get boats signed in a race by member id")
    void getBoatsSignedInToRaceByMemberId() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        Member m2 = new Member();

        m2.setUsername("fresh");
        m2.setFiscalCode("fresh");
        m2.setPassword("fresh");
        m2.setAddress("fresh");
        m2.setName("fresh");
        m2.setSurname("fresh");

        memberRepository.save(m2);

        Boat b1 = new Boat();


        b1.setLength(1.1);
        b1.setMembersBoat(m1);
        b1.setName("blu");

        boatRepository.save(b1);

        Boat b2 = new Boat();


        b2.setLength(1.1);
        b2.setMembersBoat(m1);
        b2.setName("rosso");

        boatRepository.save(b2);

        Boat b3 = new Boat();


        b3.setLength(1.1);
        b3.setMembersBoat(m2);
        b3.setName("azzurro");

        boatRepository.save(b3);

        Boat b4 = new Boat();


        b4.setLength(1.1);
        b4.setMembersBoat(m2);
        b4.setName("magenta");

        boatRepository.save(b4);


        Race race1 = new Race();

        race1.setName("Gara bella");
        race1.setPrice(1.1);
        race1.setAward(10.1);
        race1.setDate(LocalDate.now().plusWeeks(2));

        raceRepository.save(race1);

        RaceFee raceFee1 = new RaceFee();

        raceFee1.setPrice(race1.getPrice());
        raceFee1.setRacesRaceFee(race1);
        raceFee1.setBoatsRaceFee(b1);
        raceFee1.setMembersRaceFee(m1);
        raceFee1.setPaymentDate(LocalDate.now());

        raceFeeRepository.save(raceFee1);

        Long raceId = race1.getId();
        Long member1Id = m1.getId();

        ResponseEntity<Iterable<Boat>> responseEntity1 = boatRest.getBoatsSignedInByMember(raceId, member1Id);
        ResponseEntity<Iterable<Boat>> responseEntity2 = boatRest.getBoatsSignedInByMember(raceId + 1, member1Id);
        ResponseEntity<Iterable<Boat>> responseEntity3 = boatRest.getBoatsSignedInByMember(raceId, member1Id + 2);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity3.getStatusCode());

    }


}