package me.ollari.CVbackend.Member;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    @Disabled
    void setUp() {
    }

    @AfterEach
    @Disabled
    void tearDown() {
    }

    @Test()
    @DisplayName("create two members with equal values when values need to be unique")
    void createMemberUnique(){
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


        assertThrows(RuntimeException.class, () -> {
            memberRepository.save(m1);
            memberRepository.save(m2);
        });

    }

    @Test
    @DisplayName("Create member with null values for not null fields")
    void createMemberNull() {
        Member m = new Member();

        assertThrows(RuntimeException.class, () -> memberRepository.save(m));
    }


}