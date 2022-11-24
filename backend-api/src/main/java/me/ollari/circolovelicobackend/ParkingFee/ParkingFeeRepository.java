package me.ollari.circolovelicobackend.ParkingFee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia usata per estendere la classe JpaRepository e quindi ottenere tutte le funzioni utili
 * ad un'applicazione di tipo CRUD.
 */
public interface ParkingFeeRepository extends JpaRepository<ParkingFee, Long> {
}
