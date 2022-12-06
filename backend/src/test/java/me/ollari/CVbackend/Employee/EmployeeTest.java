package me.ollari.CVbackend.Employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class EmployeeTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRest employeeRest;

    @BeforeEach
    void setUp() {
        if (employeeRepository.findAll().size() > 0) {
            employeeRepository.deleteAll();
        }
    }

    @AfterEach
    void tearDown() {
        if (employeeRepository.findAll().size() > 0) {
            employeeRepository.deleteAll();
        }
    }

    @Test
    @DisplayName("Create two employee with same unique usernames")
    void createEmployeeUnique() {
        Employee e1 = new Employee();
        Employee e2 = new Employee();

        e1.setUsername("admin");
        e1.setPassword("password");

        employeeRepository.save(e1);

        e2.setUsername("admin");
        e2.setPassword("password");


        assertThrows(RuntimeException.class, () -> {
            employeeRepository.save(e2);
        });
    }

    @Test
    @DisplayName("Create employee with null username")
    void createEmployeeNullUsername() {
        Employee e1 = new Employee();

        e1.setPassword("password");


        assertThrows(RuntimeException.class, () -> {
            employeeRepository.save(e1);
        });
    }

    @Test
    @DisplayName("Get employees using rest end point")
    void getEmployeesRest() {
        Employee e1 = new Employee();

        e1.setUsername("e1");
        e1.setPassword("password");

        Employee e2 = new Employee();

        e2.setUsername("e2");
        e2.setPassword("password");

        employeeRepository.save(e1);
        employeeRepository.save(e2);

        List<Employee> employeeList = (List<Employee>) employeeRest.getEmployees();

        assertEquals(2, employeeList.size());
    }

    @Test
    @DisplayName("Get employees by id using rest end point")
    void getEmployeesByIdRest() {
        Employee e1 = new Employee();

        e1.setUsername("e1");
        e1.setPassword("password");

        Employee e2 = new Employee();

        e2.setUsername("e2");
        e2.setPassword("password");

        employeeRepository.save(e1);
        employeeRepository.save(e2);

        Long employeeId = employeeRepository.findAll().get(0).getId();

        ResponseEntity<Employee> employeeResponseEntity1 = employeeRest.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, employeeResponseEntity1.getStatusCode());

        ResponseEntity<Employee> employeeResponseEntity2 = employeeRest.getEmployeeById(employeeId + 5);

        assertEquals(HttpStatus.NOT_FOUND, employeeResponseEntity2.getStatusCode());
    }

    @Test
    @DisplayName("Get employees by username using rest end point")
    void getEmployeesByUsernameRest() {
        Employee e1 = new Employee();

        e1.setUsername("e1");
        e1.setPassword("password");

        Employee e2 = new Employee();

        e2.setUsername("e2");
        e2.setPassword("password");

        employeeRepository.save(e1);
        employeeRepository.save(e2);
        

        ResponseEntity<Employee> employeeResponseEntity1 = employeeRest.getEmployeeByUsername("e1");

        assertEquals(HttpStatus.OK, employeeResponseEntity1.getStatusCode());

        ResponseEntity<Employee> employeeResponseEntity2 = employeeRest.getEmployeeByUsername("qweqweqwe");

        assertEquals(HttpStatus.NOT_FOUND, employeeResponseEntity2.getStatusCode());
    }

    @Test
    @DisplayName("Post employees using rest end point")
    void postEmployeeUsingEndPoint() {
        Employee e1 = new Employee();

        e1.setUsername("e1");
        e1.setPassword("password");

        ResponseEntity<Employee> employeeResponseEntity = employeeRest.createEmployee(e1);


        Optional<Employee> employee = employeeRepository.findByUsername("e1");

        assertEquals(true, employee.isPresent());
        assertEquals(e1.getUsername(), employee.get().getUsername());
        assertEquals(e1.getPassword(), employee.get().getPassword());

    }

    @Test
    @DisplayName("Delete employees using rest end point")
    void deleteEmployeeUsingREST() {
        Employee e1 = new Employee();

        e1.setUsername("e1");
        e1.setPassword("password");


        employeeRepository.save(e1);

        Long employeeId = employeeRepository.findAll().get(0).getId();

        ResponseEntity<Employee> employeeResponseEntity1 = employeeRest.deleteEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, employeeResponseEntity1.getStatusCode());

        ResponseEntity<Employee> employeeResponseEntity2 = employeeRest.deleteEmployeeById(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, employeeResponseEntity2.getStatusCode());
    }
}