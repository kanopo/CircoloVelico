package me.ollari.CVbackend.Boat;

import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

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
    private BoatRest boatRest;

    @BeforeEach
    void setUp() {
        if (boatRepository.findAll().size() > 0) {
            boatRepository.deleteAll();
        }

        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
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
    




}