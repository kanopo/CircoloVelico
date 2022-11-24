package me.ollari.circolovelicobackend.AnnualFee;

import me.ollari.circolovelicobackend.Member.Member;
import me.ollari.circolovelicobackend.Member.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Questa classe viene usata come uno degli endpoint del'API che vine usata per comunicare tra DB(tramite {@link AnnualFeeRepository}) e GUI.
 * In questa classe vengono racchiuse tutte quelle chiamate API che restituiscono oggetti o liste di oggetti inerenti alla
 * classe {@link AnnualFee}.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@RestController
public class AnnualFeeRest {
    private final AnnualFeeRepository annualFeeRepository;
    private final MemberRepository memberRepository;

    /**
     * Questo costruttore viene utilizzato per inizializzare le repository che verranno utilizzate nelle chiamate del'API.
     *
     * @param annualFeeRepository repository relativa a tutte le operazioni di tipo CRUD inerente alle tasse annuali d'iscrizione degli utenti.
     * @param memberRepository    repository relativa a tutte le operazioni di tipo CRUD inerente ai membri.
     */
    public AnnualFeeRest(AnnualFeeRepository annualFeeRepository, MemberRepository memberRepository) {
        this.annualFeeRepository = annualFeeRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce tutte le tasse annuali
     *
     * @return tutte le tasse annuali conservate nel database, se il DB dovesse essere vuoto, si riceverebbe un array vuoto
     */
    @GetMapping("/annualFees")
    Iterable<AnnualFee> getAnnualFees() {
        return annualFeeRepository.findAll();
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce una tassa annuale in base all'id fornito
     *
     * @param annualFeeId id della tassa
     * @return tassa annuale con id corrispondente, se l'id non esiste si riceve un 404(non trovato)
     */
    @GetMapping("/annualFees/{annualFeeId}")
    ResponseEntity<AnnualFee> getAnnualFeeById(@PathVariable Long annualFeeId) {
        AnnualFee annualFee = annualFeeRepository.findById(annualFeeId).orElse(null);

        if (annualFee != null) {
            return new ResponseEntity<>(annualFee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * EndPoint di tipo GET della RESP API che restituisce tutte le tasse annuali in base all'id del'utente fornito
     *
     * @param memberId id dell'utente
     * @return tutte le tasse associate all'utente, se l'id non esiste si riceve un 404(non trovato)
     */
    @GetMapping("/annualFees/memberId/{memberId}")
    ResponseEntity<Iterable<AnnualFee>> annualFeesByMemberId(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            Iterable<AnnualFee> annualFees = member.getAnnualFees();
            return new ResponseEntity<>(annualFees, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * EndPoint di tipo PUT della RESP API che crea una tassa annuale e la associa al rispettivo membro
     *
     * @param memberId  id dell'utente al quale si associa la tassa
     * @param annualFee on oggetto che rappresenta tutte le specifiche della tassa da aggiungere
     * @return 201 se la tassa viene creata, 406 se non viene creata(not acceptable)
     */
    @PutMapping("/annualFees/memberId/{memberId}")
    ResponseEntity<AnnualFee> createAnnualFee(@PathVariable Long memberId, @RequestBody AnnualFee annualFee) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            annualFee.setMembersAnnualFee(member);

            annualFeeRepository.save(annualFee);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
