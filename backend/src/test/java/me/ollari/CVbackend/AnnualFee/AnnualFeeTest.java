package me.ollari.CVbackend.AnnualFee;

import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import me.ollari.CVbackend.Member.MemberRest;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class AnnualFeeTest {

    @Autowired
    private AnnualFeeRepository annualFeeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnnualFeeRest annualFeeRest;

    @Autowired
    private MemberRest memberRest;

    @BeforeEach
    void setUp() {
        if (annualFeeRepository.findAll().size() > 0) {
            annualFeeRepository.deleteAll();
        }

        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
        }
    }

    @AfterEach
    void tearDown() {
        if (annualFeeRepository.findAll().size() > 0) {
            annualFeeRepository.deleteAll();
        }

        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
        }
    }

    @Test
    @DisplayName("Create annual fee for member that not exists")
    void createAnnualFeeForMemberThatNotExists() {
        AnnualFee annualFee = new AnnualFee();

        annualFee.setStart(LocalDate.now());
        annualFee.setEnd(LocalDate.now().plusYears(1));
        annualFee.setPrice(12.3);

        assertThrows(RuntimeException.class, () -> annualFeeRepository.save(annualFee));
    }

    @Test
    @DisplayName("Member should have unique annual fees")
    void memberShouldHaveUniqueAnnualFees() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        AnnualFee annualFee1 = new AnnualFee();

        annualFee1.setStart(LocalDate.now());
        annualFee1.setEnd(LocalDate.now().plusYears(1));
        annualFee1.setPrice(1.1);
        annualFee1.setMembersAnnualFee(m1);

        annualFeeRepository.save(annualFee1);

        AnnualFee annualFee2 = new AnnualFee();

        annualFee2.setStart(LocalDate.now());
        annualFee2.setEnd(LocalDate.now().plusYears(1));
        annualFee2.setPrice(1.1);
        annualFee2.setMembersAnnualFee(m1);

        assertThrows(RuntimeException.class, () -> {
            annualFeeRepository.save(annualFee2);
        });
    }

    @Test
    @DisplayName("Annual fee must have start date")
    void annualFeesMustHaveStartDate() {
        AnnualFee annualFee = new AnnualFee();

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        annualFee.setEnd(LocalDate.now().plusYears(1));
        annualFee.setPrice(1.1);

        annualFee.setMembersAnnualFee(m1);

        assertThrows(RuntimeException.class, () -> annualFeeRepository.save(annualFee));

    }

    @Test
    @DisplayName("Annual fee must have end date")
    void annualFeesMustHaveEndDate() {
        AnnualFee annualFee = new AnnualFee();

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        annualFee.setStart(LocalDate.now());
        annualFee.setPrice(1.1);

        annualFee.setMembersAnnualFee(m1);

        assertThrows(RuntimeException.class, () -> annualFeeRepository.save(annualFee));

    }

    @Test
    @DisplayName("Annual fee must have price")
    void annualFeesMustHavePrice() {
        AnnualFee annualFee = new AnnualFee();

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        annualFee.setStart(LocalDate.now());
        annualFee.setStart(LocalDate.now().plusYears(1));

        annualFee.setMembersAnnualFee(m1);

        assertThrows(RuntimeException.class, () -> annualFeeRepository.save(annualFee));

    }

    @Test
    @DisplayName("Removing annual fee should leave the member account in the DB")
    void removingAnnualFeeShouldLeaveMember() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        AnnualFee annualFee = new AnnualFee();

        annualFee.setStart(LocalDate.now());
        annualFee.setEnd(LocalDate.now().plusYears(1));
        annualFee.setPrice(1.1);
        annualFee.setMembersAnnualFee(m1);

        annualFeeRepository.save(annualFee);

        annualFeeRepository.deleteAll();

        assertEquals(1, memberRepository.findAll().size());
    }

    @Test
    @DisplayName("Removing member should remove all the annual fees related to the member account")
    void removingMemberShouldRemoveAnnualFee() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        AnnualFee annualFee = new AnnualFee();

        annualFee.setStart(LocalDate.now());
        annualFee.setEnd(LocalDate.now().plusYears(1));
        annualFee.setPrice(1.1);
        annualFee.setMembersAnnualFee(m1);

        annualFeeRepository.save(annualFee);

        memberRepository.deleteAll();

        assertEquals(0, memberRepository.findAll().size());
        assertEquals(0, annualFeeRepository.findAll().size());
    }
    
    @Test
    @DisplayName("Testing get end point")
    void getAnnualFeesUsingRestEndpoint() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        AnnualFee annualFee = new AnnualFee();

        annualFee.setStart(LocalDate.now());
        annualFee.setEnd(LocalDate.now().plusYears(1));
        annualFee.setPrice(1.1);
        annualFee.setMembersAnnualFee(m1);

        annualFeeRepository.save(annualFee);

        List<AnnualFee> annualFees = (List<AnnualFee>) annualFeeRest.getAnnualFees();

        assertEquals(1, annualFees.size());
        assertEquals(m1.getUsername(), annualFees.get(0).getMembersAnnualFee().getUsername());


    }

    @Test
    @DisplayName("Testing get end point by member id")
    void getAnnualFeesUsingRestEndpointByMemberId() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        AnnualFee annualFee = new AnnualFee();

        annualFee.setStart(LocalDate.now());
        annualFee.setEnd(LocalDate.now().plusYears(1));
        annualFee.setPrice(1.1);
        annualFee.setMembersAnnualFee(m1);

        annualFeeRepository.save(annualFee);

        Long memberId = memberRepository.findAll().get(0).getId();

        ResponseEntity<Iterable<AnnualFee>> annualFeesResponse1 = annualFeeRest.annualFeesByMemberId(memberId);


        assertEquals(HttpStatus.OK, annualFeesResponse1.getStatusCode());


        ResponseEntity<Iterable<AnnualFee>> annualFeesResponse2 = annualFeeRest.annualFeesByMemberId(memberId + 1);


        assertEquals(HttpStatus.NOT_FOUND, annualFeesResponse2.getStatusCode());

    }

    @Test
    @DisplayName("Testing get end point by id")
    void getAnnualFeeByEndPointWithId() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        AnnualFee annualFee = new AnnualFee();

        annualFee.setStart(LocalDate.now());
        annualFee.setEnd(LocalDate.now().plusYears(1));
        annualFee.setPrice(1.1);
        annualFee.setMembersAnnualFee(m1);

        annualFeeRepository.save(annualFee);

        Long annualFeeId = annualFeeRepository.findAll().get(0).getId();

        ResponseEntity<AnnualFee> annualFeesResponse1 = annualFeeRest.getAnnualFeeById(annualFeeId);


        assertEquals(HttpStatus.OK, annualFeesResponse1.getStatusCode());

        System.out.println(annualFeesResponse1.getBody());


        ResponseEntity<AnnualFee> annualFeesResponse2 = annualFeeRest.getAnnualFeeById(annualFeeId + 1);


        assertEquals(HttpStatus.NOT_FOUND, annualFeesResponse2.getStatusCode());

        System.out.println(annualFeesResponse2.getBody());

    }

    @Test
    @DisplayName("Testing put end point")
    void putAnnualFeeToMemberByREST() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        AnnualFee annualFee1 = new AnnualFee();

        annualFee1.setStart(LocalDate.now());
        annualFee1.setEnd(LocalDate.now().plusYears(1));
        annualFee1.setPrice(1.1);

        AnnualFee annualFee2 = new AnnualFee();

        annualFee2.setStart(LocalDate.now());
        annualFee2.setEnd(LocalDate.now().plusYears(1));
        annualFee2.setPrice(1.1);


        Long memberId = memberRepository.findAll().get(0).getId();

        ResponseEntity<AnnualFee> annualFeeResponseEntity1 = annualFeeRest.createAnnualFee(memberId, annualFee1);

        assertEquals(HttpStatus.CREATED, annualFeeResponseEntity1.getStatusCode());

        ResponseEntity<AnnualFee> annualFeeResponseEntity2 = annualFeeRest.createAnnualFee(memberId + 1, annualFee1);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, annualFeeResponseEntity2.getStatusCode());

    }
}
