package me.ollari.CVbackend.Employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Questa classe viene usata come uno degli endpoint del'API che vine usata per comunicare tra DB(tramite {@link EmployeeRepository}) e GUI.
 * In questa classe vengono racchiuse tutte quelle chiamate API che restituiscono oggetti o liste di oggetti inerenti alla
 * classe {@link Employee}.
 *
 * @author Dmitri Ollari
 * @since 24-11-2022
 */
@RestController
public class EmployeeRest {
    private final EmployeeRepository employeeRepository;

    /**
     * Questo costruttore viene utilizzato per inizializzare le repository che verranno utilizzate nelle chiamate del'API.
     * @param employeeRepository repository usata per operazioni crud inerenti agli impiegati
     */
    public EmployeeRest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    /**
     * EndPoint di tipo GET della RESP API utilizzato per richiedere tutti gli impiegati del circolo
     * @return lista contenente gli impiegati
     */
    @GetMapping("/employee")
    Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per ricevere i dati di un impiegato in base all'id
     * @param employeeId id dell'utente
     * @return oggetto contenente i dati dell'impiegato, se l'id non esiste viene ricevuto il codice 404
     */
    @GetMapping("/employee/{employeeId}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * EndPoint di tipo GET della RESP API utilizzato per ricevere i dati di un impiegato in base al'username
     * @param username usename dell'utente usato per il login
     * @return oggetto employee contenente i dati dell'utente
     */
    @GetMapping("/employee/username/{username}")
    ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username) {

        Employee employee = employeeRepository.findByUsername(username).orElse(null);

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }


    }


    /**
     * EndPoint di tipo GET della RESP API utilizzato per creare un nuovo account per l'impiegato
     * @param employee oggetto contenente i dati del neo impiegato
     * @return 201 se l'account è stato creato correttamente, 406 se l'username è già presente nel DB
     */
    @PostMapping("/employee")
    ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Error e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * EndPoint di tipo DELETE della RESP API utilizzato per eliminare l'account di un impiegato
     * @param employeeId id dell'impiegato
     * @return 200 se eliminato con successo, 404 se l'id non esiste nel DB
     */
    @DeleteMapping("/employee/{employeeId}")
    ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
