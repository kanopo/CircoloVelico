package me.ollari.CVbackend.RaceFee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.ollari.CVbackend.Boat.Boat;
import me.ollari.CVbackend.Member.Member;
import me.ollari.CVbackend.Race.Race;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Classe usata per rappresentare le tasse di partecipazione alle gare presente nel database,
 * questa classe viene usata per tenere traccia delle iscrizioni e dei pagamenti inerenti a membri, barca dei membri e gare.
 * Viene usata per creare la tabella annual_fee nel DB e viene utilizzata nelle relative call del'API.
 * I metodi Getter, Setter, ToString e Costruttore senza argomenti sono rimpiazzati da lombok per evitare boilerplate.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "race_fee")
public class RaceFee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RACE_FEE_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "price", nullable = false)
    private Double price;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member membersRaceFee;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "race_id")
    private Race racesRaceFee;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "boat_id")
    private Boat boatsRaceFee;
}
