package me.ollari.circolovelicobackend.Employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Classe usata per rappresentare gli impiegati del circolo presenti nel database.
 * Viene usata per creare la tabella employee nel DB e viene utilizzata nelle relative call del'API.
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
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
