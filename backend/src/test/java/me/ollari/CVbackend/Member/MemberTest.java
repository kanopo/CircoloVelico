package me.ollari.CVbackend.Member;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.Assert.*;

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


        assertThrows(RuntimeException.class, () -> memberRepository.save(m2));

    }

    @Test
    @DisplayName("Create member with null fiscal code")
    void createMemberNullFiscalCode() {
        Member m = new Member();

        m.setUsername("dmo");
        m.setPassword("dmo");
        m.setAddress("dmo");
        m.setName("dmo");
        m.setSurname("dmo");

        assertThrows(RuntimeException.class, () -> memberRepository.save(m));
    }

    @Test
    @DisplayName("Create member with null username")
    void createMemberNullUserName() {
        Member m = new Member();

        m.setFiscalCode("dmo");
        m.setPassword("dmo");
        m.setAddress("dmo");
        m.setName("dmo");
        m.setSurname("dmo");

        assertThrows(RuntimeException.class, () -> memberRepository.save(m));
    }

    @Test
    @DisplayName("Create member with null password")
    void createMemberNullPassword() {
        Member m = new Member();

        m.setFiscalCode("dmo");
        m.setUsername("dmo");
        m.setAddress("dmo");
        m.setName("dmo");
        m.setSurname("dmo");

        assertThrows(RuntimeException.class, () -> memberRepository.save(m));
    }

    @Test()
    @DisplayName("get members using rest")
    void getMembersUsingRest() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        List<Member> members = (List<Member>) memberRest.getMembers();

        assertEquals(1, members.size());

    }

    @Test()
    @DisplayName("get members by id with rest")
    void getMemberByIdUsingRest() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        Long memberId = memberRepository.findAll().get(0).getId();

        ResponseEntity<Member> memberById = memberRest.getMemberById(memberId);
        ResponseEntity<Member> memberByIdFailed = memberRest.getMemberById(memberId + 1);

        assertEquals(HttpStatus.OK, memberById.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, memberByIdFailed.getStatusCode());

        Member member = memberById.getBody();

        assertEquals("dmo", member.getUsername());

    }

    @Test
    @DisplayName("Get member by username with rest")
    void getMemberByUsername() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        ResponseEntity<Member> member1 = memberRest.findMemberByUsername("dmo");
        ResponseEntity<Member> member2 = memberRest.findMemberByUsername("qwe");

        assertEquals(member1.getStatusCode(), HttpStatus.OK);
        assertEquals(member1.getBody().getFiscalCode(), "dmo");

        assertEquals(member2.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Post member with rest")
    void postMemberWithRest() {

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

        ResponseEntity<Member> responseFirstMemberSave = memberRest.createMember(m1);

        assertEquals(HttpStatus.CREATED, responseFirstMemberSave.getStatusCode());

        assertEquals(true, memberRepository.findByUsername("dmo").isPresent());

        ResponseEntity<Member> responseEntitySecondSave = memberRest.createMember(m2);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntitySecondSave.getStatusCode());

    }

    @Test
    @DisplayName("Mod member using put mapping in rest")
    void modMemberUsingRest() {

        Member oldMember = new Member("dmo", "dmo", "dmo", "dmo", "dmo", "dmo");

        Member moddedMember = new Member();

        memberRepository.save(oldMember);

        moddedMember.setName("qwe");

        Long memberIdToMod = memberRepository.findByUsername("dmo").get().getId();


        ResponseEntity<Member> responseEntity1 = memberRest.modifyMemberById(memberIdToMod, moddedMember);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ResponseEntity<Member> responseEntity2 = memberRest.modifyMemberById(memberIdToMod + 1, moddedMember);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());

    }

    @Test
    @DisplayName("Delete member with rest")
    void deleteMemberWithRest() {
        Member m1 = new Member();

        m1.setUsername("dmo");
        m1.setFiscalCode("dmo");
        m1.setPassword("dmo");
        m1.setAddress("dmo");
        m1.setName("dmo");
        m1.setSurname("dmo");

        memberRepository.save(m1);

        Long memberId = memberRepository.findByUsername("dmo").get().getId();

        ResponseEntity<Member> responseEntity1 = memberRest.deleteMember(memberId);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ResponseEntity<Member> responseEntity2 = memberRest.deleteMember(memberId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }
}
