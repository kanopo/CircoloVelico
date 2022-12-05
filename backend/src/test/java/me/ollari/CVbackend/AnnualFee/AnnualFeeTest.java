package me.ollari.CVbackend.AnnualFee;

import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.Assert.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class AnnualFeeTest {

    @Autowired
    private AnnualFeeRepository annualFeeRepository;

    @Autowired
    private MemberRepository memberRepository;

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
}
