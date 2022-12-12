package me.ollari.CVbackend.RaceFee;

import me.ollari.CVbackend.Boat.Boat;
import me.ollari.CVbackend.Boat.BoatRepository;
import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import me.ollari.CVbackend.Race.Race;
import me.ollari.CVbackend.Race.RaceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class RaceFeeTest {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private RaceFeeRepository raceFeeRepository;

    @Autowired
    private RaceFeeRest raceFeeRest;

    @BeforeEach
    void setUp() {
        raceRepository.deleteAll();
        memberRepository.deleteAll();
        boatRepository.deleteAll();
        raceFeeRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        raceRepository.deleteAll();
        memberRepository.deleteAll();
        boatRepository.deleteAll();
        raceFeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Add race fee with null payment date")
    void addRaceFeeWithNullPaymentDate () {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        // racefee

        RaceFee rf1 = new RaceFee();

        rf1.setPrice(r1.getPrice());
        rf1.setMembersRaceFee(m1);
        rf1.setBoatsRaceFee(b1);
        rf1.setRacesRaceFee(r1);

       assertThrows(RuntimeException.class, () -> raceFeeRepository.save(rf1));
    }

    @Test
    @DisplayName("Add race fee with null price")
    void addRaceFeeWithNullPrice () {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        // racefee

        RaceFee rf1 = new RaceFee();

        rf1.setPaymentDate(LocalDate.now());
        rf1.setMembersRaceFee(m1);
        rf1.setBoatsRaceFee(b1);
        rf1.setRacesRaceFee(r1);

        assertThrows(RuntimeException.class, () -> raceFeeRepository.save(rf1));
    }

    @Test
    @DisplayName("Add race fee with null member")
    void addRaceFeeWithNullMember () {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        // racefee

        RaceFee rf1 = new RaceFee();

        rf1.setPaymentDate(LocalDate.now());
        rf1.setPrice(r1.getPrice());
        rf1.setBoatsRaceFee(b1);
        rf1.setRacesRaceFee(r1);

        assertThrows(RuntimeException.class, () -> raceFeeRepository.save(rf1));
    }

    @Test
    @DisplayName("Add race fee with null boat")
    void addRaceFeeWithNullBoat () {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        // racefee

        RaceFee rf1 = new RaceFee();

        rf1.setPaymentDate(LocalDate.now());
        rf1.setPrice(r1.getPrice());
        rf1.setMembersRaceFee(m1);
        rf1.setRacesRaceFee(r1);

        assertThrows(RuntimeException.class, () -> raceFeeRepository.save(rf1));
    }

    @Test
    @DisplayName("Add race fee with null race")
    void addRaceFeeWithNullRace () {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        // racefee

        RaceFee rf1 = new RaceFee();

        rf1.setPaymentDate(LocalDate.now());
        rf1.setPrice(r1.getPrice());
        rf1.setMembersRaceFee(m1);
        rf1.setBoatsRaceFee(b1);

        assertThrows(RuntimeException.class, () -> raceFeeRepository.save(rf1));
    }

    @Test
    @DisplayName("Get all race fees with rest")
    void getAllRaceFeesWithRest () {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        // racefee

        RaceFee rf1 = new RaceFee();

        rf1.setPaymentDate(LocalDate.now());
        rf1.setPrice(r1.getPrice());
        rf1.setMembersRaceFee(m1);
        rf1.setBoatsRaceFee(b1);
        rf1.setRacesRaceFee(r1);

        raceFeeRepository.save(rf1);

        Iterable<RaceFee> raceFees = raceFeeRest.getRaceFees();

        List<RaceFee> list = (List<RaceFee>) raceFees;

        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Get by id")
    void getRaceFeeById() {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        // racefee

        RaceFee rf1 = new RaceFee();

        rf1.setPaymentDate(LocalDate.now());
        rf1.setPrice(r1.getPrice());
        rf1.setMembersRaceFee(m1);
        rf1.setBoatsRaceFee(b1);
        rf1.setRacesRaceFee(r1);

        raceFeeRepository.save(rf1);

        ResponseEntity<RaceFee> responseEntity1 = raceFeeRest.getRaceFeeById(rf1.getId());

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ResponseEntity<RaceFee> responseEntity2 = raceFeeRest.getRaceFeeById(rf1.getId() + 1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    @Test
    @DisplayName("Get by member id")
    void getRaceFeeByMemberId() {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        // racefee

        RaceFee rf1 = new RaceFee();

        rf1.setPaymentDate(LocalDate.now());
        rf1.setPrice(r1.getPrice());
        rf1.setMembersRaceFee(m1);
        rf1.setBoatsRaceFee(b1);
        rf1.setRacesRaceFee(r1);

        raceFeeRepository.save(rf1);

        ResponseEntity<Iterable<RaceFee>> responseEntity1 = raceFeeRest.getRaceFeesByMemberId(m1.getId());

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ResponseEntity<Iterable<RaceFee>> responseEntity2 = raceFeeRest.getRaceFeesByMemberId(m1.getId() + 1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    @Test
    @DisplayName("Create race fee using rest")
    void createRaceFeeWithRest() {
        // member
        Member m1 = new Member();

        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");
        m1.setPassword("dmo");
        m1.setUsername("dmo");

        memberRepository.save(m1);
        // boat

        Boat b1 = new Boat();

        b1.setMembersBoat(m1);
        b1.setLength(1.1);
        b1.setName("Barca bella");

        boatRepository.save(b1);

        // race
        Race r1 = new Race();

        r1.setDate(LocalDate.now().plusMonths(2));
        r1.setAward(10.1);
        r1.setPrice(10.1);
        r1.setName("Gara bella");

        raceRepository.save(r1);

        ResponseEntity<RaceFee> responseEntity1 = raceFeeRest.createRaceFee(b1.getId(), r1.getId());

        assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());

        Boat b2 = new Boat();

        b2.setMembersBoat(m1);
        b2.setLength(1.1);
        b2.setName("Barca brutta");

        boatRepository.save(b2);

        ResponseEntity<RaceFee> responseEntity2 = raceFeeRest.createRaceFee(b2.getId(), r1.getId());

        assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());

        ResponseEntity<RaceFee> responseEntity3 = raceFeeRest.createRaceFee(b2.getId() + 1, r1.getId());

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity3.getStatusCode());

        ResponseEntity<RaceFee> responseEntity4 = raceFeeRest.createRaceFee(b2.getId(), r1.getId() + 1);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity4.getStatusCode());
    }
}