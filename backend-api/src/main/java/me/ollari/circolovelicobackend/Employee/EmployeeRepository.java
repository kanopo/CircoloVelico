package me.ollari.circolovelicobackend.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia usata per estendere la classe JpaRepository e quindi ottenere tutte le funzioni utili
 * ad un'applicazione di tipo CRUD.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
