package me.ollari.CVbackend.Member;

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


    /**
     * EndPoint di tipo GET della RESP API utilizzato per richiedere una lista di tutti i membri salvati nel db
     * @return lista di tutti i membri
     */
    @GetMapping("/members")
    Iterable<Member> getMembers() {
        return memberRepository.findAll();
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per richiedere i dati di un utente in base all'id
     * @param memberId id dell'utente del quale si vogliono ricevere i dati
     * @return oggetto member con i dati e il codice 200, se l'id non esiste viene restituito il codice 404
     */
    @GetMapping("/members/{memberId}")
    ResponseEntity<Member> getMemberById(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per richiedere un utente in base all'username
     * @param username username dell'utente che si vuole
     * @return oggetto di tipo member contenente i dati e il codice 200, se l'username non esiste nel db viene restituito il codice 404
     */
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

    /**
     * EndPoint di tipo POST della RESP API utilizzato per creare nuovi membri
     * @param member oggetto che si vuole inserire nel db
     * @return 406(not acceptable) se il CF o username sono gia' presenti nel DB, restituisce 201(created) se l'utente viene inserito e salvato correttamente
     */
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

    /**
     * EndPoint di tipo DELETE della RESP API utilizzato per eliminare l'account di un membro
     * @param memberId id dell'utente da eliminare
     * @return 404 se l'id non esiste, 200 se l'id esiste e l'utente viene eliminato
     */
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
