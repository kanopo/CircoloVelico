package me.ollari.CVbackend.AnnualFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia usata per estendere la classe JpaRepository e quindi ottenere tutte le funzioni utili
 * ad un'applicazione di tipo CRUD.
 */
public interface AnnualFeeRepository extends JpaRepository<AnnualFee, Long> {

    @Query("SELECT af FROM AnnualFee AS af WHERE af.membersAnnualFee.id = :mid")
    Optional<List<AnnualFee>> findByMemberId(@Param("mid") Long memberId);
}
