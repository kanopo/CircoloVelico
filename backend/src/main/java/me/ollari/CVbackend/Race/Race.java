package me.ollari.CVbackend.Race;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.ollari.CVbackend.RaceFee.RaceFee;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe usata per rappresentare le gare presenti nel database.
 * Viene usata per creare la tabella race nel DB e viene utilizzata nelle relative call del'API.
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
@Table(name = "race")
public class Race {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "price")
    private Double price;

    @Column(name = "name")
    private String name;

    @Column(name = "award")
    private Double award;

    @JsonIgnore
    @OneToMany(mappedBy = "racesRaceFee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<RaceFee> raceFees = new HashSet<>();

}
