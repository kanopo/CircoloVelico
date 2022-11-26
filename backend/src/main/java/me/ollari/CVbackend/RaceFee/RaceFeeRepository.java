package me.ollari.CVbackend.RaceFee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia usata per estendere la classe JpaRepository e quindi ottenere tutte le funzioni utili
 * ad un'applicazione di tipo CRUD.
 */
public interface RaceFeeRepository extends JpaRepository<RaceFee, Long> {
}
