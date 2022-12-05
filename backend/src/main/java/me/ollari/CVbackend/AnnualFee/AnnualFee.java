package me.ollari.CVbackend.AnnualFee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.ollari.CVbackend.Member.Member;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Classe usata per rappresentare la tassa d'iscrizione annuale presente nel
 * database,
 * questa classe viene usata per tener traccia dell'abbonamento annuale di ogni
 * membro del circolo.
 * Viene usata per creare la tabella annual_fee nel DB e viene utilizzata nelle
 * relative call del'API.
 * I metodi Getter, Setter, ToString e Costruttore senza argomenti sono
 * rimpiazzati da lombok per evitare boilerplate.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "AnnualFee")
@Table(name = "annual_fee", indexes = {
        @Index(name = "unique_annual_fee_for_period", columnList = "start,end,member_id", unique = true) })
public class AnnualFee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @SequenceGenerator(name = "annual_fee_seq", allocationSize = 1, sequenceName
    // = "annual_fee_seq")
    @Column(name = "id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "start", nullable = false)
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end", nullable = false)
    private LocalDate end;

    @Column(name = "price")
    private Double price;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "member_id", nullable = false)
    private Member membersAnnualFee;
}
