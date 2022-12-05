package me.ollari.CVbackend.Member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    MemberRest memberRest;

    @BeforeEach
    void setUp() {
        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
        }
    }

    @AfterEach
    void tearDown() {
        if (memberRepository.findAll().size() > 0) {
            memberRepository.deleteAll();
        }
    }


    @Test()
    @DisplayName("Create two members with equal values when values need to be unique")
    void createMemberUnique() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");


        Member m2 = new Member();

        m2.setUsername("dmo");
        m2.setFiscalCode("dmo");
        m2.setPassword("dmo");
        m2.setAddress("dmo");
        m2.setName("dmo");
        m2.setSurname("dmo");

        memberRepository.save(m1);


        assertThrows(RuntimeException.class, () -> {

            memberRepository.save(m2);
        });

    }

    @Test
    @DisplayName("Create member with null values for not null fields")
    void createMemberNull() {
        Member m = new Member();

        assertThrows(RuntimeException.class, () -> memberRepository.save(m));
    }

    @Test
    void ciao() {

        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        ResponseEntity<Member> memberHttpResponse = memberRest.createMember(m1);

        System.out.println(memberRepository.findAll());
    }


}
