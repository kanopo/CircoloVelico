package me.ollari.CVbackend.Member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
    public MemberRepository memberRepository;

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
    public Iterable<Member> getMembers() {
        return memberRepository.findAll();
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per richiedere i dati di un utente in base all'id
     * @param memberId id dell'utente del quale si vogliono ricevere i dati
     * @return oggetto member con i dati e il codice 200, se l'id non esiste viene restituito il codice 404
     */
    @GetMapping("/members/{memberId}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long memberId) {
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
    public ResponseEntity<Member> findMemberByUsername(@PathVariable String username) {

        Member member = memberRepository.findByUsername(username).orElse(null);

        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
        {
            return new ResponseEntity<>(member, HttpStatus.OK);
        }

    }

    /**
     * EndPoint di tipo POST della RESP API utilizzato per creare nuovi membri
     * @param member oggetto che si vuole inserire nel db
     * @return 406(not acceptable) se il CF o username sono gia' presenti nel DB, restituisce 201(created) se l'utente viene inserito e salvato correttamente
     */
    @PostMapping("/members")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
/*
        Iterable<Member> allMembers = memberRepository.findAll();

        for (Member m : allMembers) {
            if (m.getFiscalCode().equals(member.getFiscalCode()) || m.getUsername().equals(member.getUsername())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

 */
        try {
            memberRepository.save(member);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * EndPoint di tipo PUT della RESP API utilizzato per modificare alcuni parametri del member
     * @param memberId id dell'utente da modificare
     * @param modded dati modificati
     * @return 404 se l'id non esiste e 200 se la modifica e' avvenuta con successo
     */
    @PutMapping("/members/{memberId}")
    public ResponseEntity<Member> modifyMemberById(@PathVariable Long memberId, @RequestBody Member modded) {
        Member old = memberRepository.findById(memberId).orElse(null);

        if (old != null) {

            // id of member exists
            if (modded.getName() != null && !Objects.equals(old.getName(), modded.getName()) && !modded.getName().isBlank() && !modded.getName().isEmpty()) {
                old.setName(modded.getName());
            }

            if (modded.getAddress() != null && !Objects.equals(old.getAddress(), modded.getAddress()) && !modded.getAddress().isBlank() && !modded.getAddress().isEmpty()) {
                old.setAddress(modded.getAddress());
            }

            if (modded.getSurname() != null && !Objects.equals(old.getSurname(), modded.getSurname()) && !modded.getSurname().isBlank() && !modded.getSurname().isEmpty()) {
                old.setSurname(modded.getSurname());
            }

            memberRepository.save(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * EndPoint di tipo DELETE della RESP API utilizzato per eliminare l'account di un membro
     * @param memberId id dell'utente da eliminare
     * @return 404 se l'id non esiste, 200 se l'id esiste e l'utente viene eliminato
     */
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Member> deleteMember(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            memberRepository.delete(member);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
