package me.ollari.circolovelicobackend.Member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Questa classe viene usata come uno degli endpoint del'API che vine usata per comunicare tra DB(tramite {@link MemberRepository}) e GUI.
 * In questa classe vengono racchiuse tutte quelle chiamate API che restituiscono oggetti o liste di oggetti inerenti alla
 * classe {@link Member}.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@RestController
public class MemberRest {
    private final MemberRepository memberRepository;

    /**
     * Questo costruttore viene utilizzato per inizializzare le repository che verranno utilizzate nelle chiamate del'API.
     * @param memberRepository repository usata per operazioni crud inerenti dei membri
     */
    public MemberRest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @GetMapping("/members")
    Iterable<Member> getMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/members/{memberId}")
    ResponseEntity<Member> getMemberById(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/members/username/{username}")
    ResponseEntity<Member> findMemberByUsername(@PathVariable String username) {
        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            if (member.getUsername().equals(username)) {
                return new ResponseEntity<>(member, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/members")
    ResponseEntity<Member> createMember(@RequestBody Member member) {
        Iterable<Member> allMembers = memberRepository.findAll();

        for (Member m : allMembers) {
            if (m.getFiscalCode().equals(member.getFiscalCode()) || m.getUsername().equals(member.getUsername())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        try {
            memberRepository.save(member);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/members/{memberId}")
    ResponseEntity<Member> deleteMember(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            memberRepository.delete(member);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
