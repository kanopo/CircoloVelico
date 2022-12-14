package me.ollari.CVbackend.Boat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.ParkingFee.ParkingFee;
import me.ollari.CVbackend.RaceFee.RaceFee;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe usata per rappresentare l'oggetto barca presente nel database.
 * Viene usata per creare la tabella boat nel DB e viene utilizzata nelle relative call del'API.
 * I metodi Getter, Setter, ToString e Costruttore senza argomenti sono rimpiazzati da lombok per evitare boilerplate.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Boat")
@Table(name = "boat")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@SequenceGenerator(name = "boat_seq", allocationSize = 1, sequenceName = "boat_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "length", nullable = false)
    private Double length;

    @JsonIgnore()
    @ManyToOne()
    @JoinColumn(name = "member_id", nullable = false)
    private Member membersBoat;

    @JsonIgnore
    @OneToMany(mappedBy = "boatsParkingFee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<ParkingFee> parkingFees = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "boatsRaceFee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<RaceFee> raceFees = new HashSet<>();


    public Boat(String name, Double length, Member member) {
        this.name = name;
        this.length = length;
        this.membersBoat = member;
    }
}
