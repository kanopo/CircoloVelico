package me.ollari.CVbackend.ParkingFee;

import me.ollari.CVbackend.Boat.Boat;
import me.ollari.CVbackend.Boat.BoatRepository;
import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
class ParkingFeeTest {

    @Autowired
    private ParkingFeeRest parkingFeeRest;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ParkingFeeRepository parkingFeeRepository;


    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        parkingFeeRepository.deleteAll();
        boatRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
        parkingFeeRepository.deleteAll();
        boatRepository.deleteAll();
    }

    @Test
    @DisplayName("Create parking fee with null start date")
    void createParkingFeeWithNUllStart() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);


        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        ParkingFee pf1 = new ParkingFee();

        pf1.setEnd(LocalDate.now().plusMonths(2));
        pf1.setMembersParkingFees(m1);
        pf1.setBoatsParkingFee(b1);
        pf1.setPrice(12.1);

        assertThrows(RuntimeException.class, () -> parkingFeeRepository.save(pf1));

    }

    @Test
    @DisplayName("Create parking fee with null end date")
    void createParkingFeeWithNUllEnd() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);


        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        ParkingFee pf1 = new ParkingFee();

        pf1.setStart(LocalDate.now().minusMonths(2));
        pf1.setMembersParkingFees(m1);
        pf1.setBoatsParkingFee(b1);
        pf1.setPrice(12.1);

        assertThrows(RuntimeException.class, () -> parkingFeeRepository.save(pf1));

    }

    @Test
    @DisplayName("Create parking fee with null price")
    void createParkingFeeWithNUllPrice() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);


        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        ParkingFee pf1 = new ParkingFee();

        pf1.setStart(LocalDate.now().minusMonths(2));
        pf1.setEnd(LocalDate.now().plusMonths(2));
        pf1.setMembersParkingFees(m1);
        pf1.setBoatsParkingFee(b1);

        assertThrows(RuntimeException.class, () -> parkingFeeRepository.save(pf1));

    }

    @Test
    @DisplayName("Create parking fee with null member")
    void createParkingFeeWithNUllMember() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);


        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        ParkingFee pf1 = new ParkingFee();

        pf1.setStart(LocalDate.now().minusMonths(2));
        pf1.setEnd(LocalDate.now().plusMonths(2));
        pf1.setBoatsParkingFee(b1);
        pf1.setPrice(12.1);

        assertThrows(RuntimeException.class, () -> parkingFeeRepository.save(pf1));

    }

    @Test
    @DisplayName("Create parking fee with null boat")
    void createParkingFeeWithNUllBoat() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);


        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        ParkingFee pf1 = new ParkingFee();

        pf1.setStart(LocalDate.now().minusMonths(2));
        pf1.setEnd(LocalDate.now().plusMonths(2));
        pf1.setMembersParkingFees(m1);
        pf1.setPrice(12.1);

        assertThrows(RuntimeException.class, () -> parkingFeeRepository.save(pf1));

    }

    @Test
    @DisplayName("Get all parking fees with rest")
    void getParkingFeesWithRest() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);


        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        ParkingFee pf1 = new ParkingFee();

        pf1.setStart(LocalDate.now().minusMonths(10));
        pf1.setEnd(LocalDate.now().minusMonths(2));
        pf1.setMembersParkingFees(m1);
        pf1.setBoatsParkingFee(b1);
        pf1.setPrice(12.1);

        ParkingFee pf2 = new ParkingFee();

        pf2.setStart(LocalDate.now().minusMonths(2));
        pf2.setEnd(LocalDate.now().plusMonths(2));
        pf2.setMembersParkingFees(m1);
        pf2.setBoatsParkingFee(b1);
        pf2.setPrice(12.1);

        parkingFeeRepository.save(pf1);
        parkingFeeRepository.save(pf2);

        Iterable<ParkingFee> parkingFees = parkingFeeRest.getParkingFees();

        List<ParkingFee> parkingFeeList = (List<ParkingFee>) parkingFees;

        assertEquals(2, parkingFeeList.size());
    }

    @Test
    @DisplayName("Get parking fees by member id with rest")
    void getParkingFeeByMemberId() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);

        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        ParkingFee pf1 = new ParkingFee();

        pf1.setStart(LocalDate.now().minusMonths(10));
        pf1.setEnd(LocalDate.now().minusMonths(2));
        pf1.setMembersParkingFees(m1);
        pf1.setBoatsParkingFee(b1);
        pf1.setPrice(12.1);

        ParkingFee pf2 = new ParkingFee();

        pf2.setStart(LocalDate.now().minusMonths(2));
        pf2.setEnd(LocalDate.now().plusMonths(2));
        pf2.setMembersParkingFees(m1);
        pf2.setBoatsParkingFee(b1);
        pf2.setPrice(12.1);

        parkingFeeRepository.save(pf1);
        parkingFeeRepository.save(pf2);

        Member m2 = new Member();
        m2.setName("q");
        m2.setSurname("q");
        m2.setUsername("q");
        m2.setPassword("q");
        m2.setFiscalCode("q");
        m2.setAddress("q");

        memberRepository.save(m2);


        ResponseEntity<Iterable<ParkingFee>> responseEntity1 = parkingFeeRest.getParkingFeesByMemberId(m1.getId());

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ResponseEntity<Iterable<ParkingFee>> responseEntity2 = parkingFeeRest.getParkingFeesByMemberId(m2.getId());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }


    @Test
    @DisplayName("Get parking fees by boat id")
    void getParkingFeesByBoatId() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);


        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        Boat b2 = new Boat();

        b2.setName("barca bella");
        b2.setLength(1.1);
        b2.setMembersBoat(m1);

        boatRepository.save(b2);

        ParkingFee pf1 = new ParkingFee();

        pf1.setStart(LocalDate.now().minusMonths(10));
        pf1.setEnd(LocalDate.now().minusMonths(2));
        pf1.setMembersParkingFees(m1);
        pf1.setBoatsParkingFee(b1);
        pf1.setPrice(12.1);

        ParkingFee pf2 = new ParkingFee();

        pf2.setStart(LocalDate.now().minusMonths(2));
        pf2.setEnd(LocalDate.now().plusMonths(2));
        pf2.setMembersParkingFees(m1);
        pf2.setBoatsParkingFee(b1);
        pf2.setPrice(12.1);

        parkingFeeRepository.save(pf1);
        parkingFeeRepository.save(pf2);

        ParkingFee pf3 = new ParkingFee();

        pf3.setStart(LocalDate.now().minusMonths(10));
        pf3.setEnd(LocalDate.now().minusMonths(2));
        pf3.setMembersParkingFees(m1);
        pf3.setBoatsParkingFee(b2);
        pf3.setPrice(12.1);

        parkingFeeRepository.save(pf3);

        ResponseEntity<Iterable<ParkingFee>> responseEntity1 = parkingFeeRest.getParkingFeeByBoatId(b2.getId());

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ResponseEntity<Iterable<ParkingFee>> responseEntity2 = parkingFeeRest.getParkingFeeByBoatId(b2.getId() + 1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());

        System.out.println(responseEntity1.getBody());
    }

    @Test
    @DisplayName("Create parking fee using rest")
    void createParkingFeeUsingRest() {
        Member m1 = new Member();
        m1.setName("dmo");
        m1.setSurname("dmo");
        m1.setUsername("dmo");
        m1.setPassword("dmo");
        m1.setFiscalCode("dmo");
        m1.setAddress("dmo");

        memberRepository.save(m1);


        Boat b1 = new Boat();

        b1.setName("barca bella");
        b1.setLength(1.1);
        b1.setMembersBoat(m1);

        boatRepository.save(b1);

        Boat b2 = new Boat();

        b2.setName("barca bella");
        b2.setLength(1.1);
        b2.setMembersBoat(m1);

        boatRepository.save(b2);

        ParkingFee pf1 = new ParkingFee();

        pf1.setStart(LocalDate.now().minusMonths(10));
        pf1.setEnd(LocalDate.now().minusMonths(2));
        pf1.setMembersParkingFees(m1);
        pf1.setPrice(12.1);

        ParkingFee pf2 = new ParkingFee();

        pf2.setStart(LocalDate.now().minusMonths(10));
        pf2.setEnd(LocalDate.now().minusMonths(2));
        pf2.setMembersParkingFees(m1);
        pf2.setPrice(12.1);

       ResponseEntity<ParkingFee> responseEntity1 = parkingFeeRest.createParkingFeeForBoat(b1.getId(), pf1);

       assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());

        ResponseEntity<ParkingFee> responseEntity2 = parkingFeeRest.createParkingFeeForBoat(b1.getId() + 2, pf2);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }





}