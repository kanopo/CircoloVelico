package me.ollari.CVbackend.Employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class EmployeeTest {

    @Autowired
    private EmployeeRepository employeeRepository;

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
}