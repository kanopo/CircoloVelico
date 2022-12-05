package me.ollari.CVbackend.ParkingFee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.ollari.CVbackend.Boat.Boat;
import me.ollari.CVbackend.Member.Member;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Classe usata per rappresentare la tassa di rimessaggio relativa alla barca presente nel database,
 * questa classe viene usata per tener traccia dei rimessaggi inerenti alle barche.
 * Viene usata per creare la tabella parking_fee nel DB e viene utilizzata nelle relative call del'API.
 * I metodi Getter, Setter, ToString e Costruttore senza argomenti sono rimpiazzati da lombok per evitare boilerplate.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "ParkingFee")
@Table(name = "parking_fee")
public class ParkingFee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@SequenceGenerator(name = "parking_fee_seq", allocationSize = 1, sequenceName = "parking_fee_seq")
    @Column(name = "id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start")
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end")
    private LocalDate end;


    @Column(name = "price")
    private Double price;

    @JsonIgnore()
    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member membersParkingFees;
    @JsonIgnore()
    @ManyToOne()
    @JoinColumn(name = "boat_id")
    private Boat boatsParkingFee;

}
