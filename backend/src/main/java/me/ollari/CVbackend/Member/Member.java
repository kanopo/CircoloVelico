package me.ollari.CVbackend.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.ollari.CVbackend.AnnualFee.AnnualFee;
import me.ollari.CVbackend.Boat.Boat;
import me.ollari.CVbackend.ParkingFee.ParkingFee;
import me.ollari.CVbackend.RaceFee.RaceFee;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Classe usata per rappresentare gli utenti presenti nel database.
 * Viene usata per creare la tabella member nel DB e viene utilizzata nelle relative call del'API.
 * I metodi Getter, Setter, ToString e Costruttore senza argomenti sono rimpiazzati da lombok per evitare boilerplate.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Member")
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@SequenceGenerator(name = "member_seq", allocationSize = 1, sequenceName = "member_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "fiscal_code", unique = true, nullable = false)
    private String fiscalCode;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "membersBoat", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Boat> boats = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "membersParkingFees", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<ParkingFee> parkingFees = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "membersAnnualFee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<AnnualFee> annualFees = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "membersRaceFee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<RaceFee> raceFees = new HashSet<>();
    

    /**
     * Costruttore usato durante la creazione dell'oggetto, non presente l'id poiché il database lo genera in automatico.
     *
     * @param name       stringa rappresentante il nome del'utente
     * @param surname    stringa rappresentante il cognome del'utente
     * @param address    stringa rappresentante l'indirizzo del'utente
     * @param fiscalCode stringa rappresentante il codice fiscale del'utente (deve essere univoco)
     * @param username   stringa rappresentante il nome utente del'utente (deve essere univoco)
     * @param password   stringa rappresentante la password del'utente
     */
    public Member(String name, String surname, String address, String fiscalCode, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.fiscalCode = fiscalCode;
        this.username = username;
        this.password = password;
    }
}
