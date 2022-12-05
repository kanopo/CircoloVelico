package me.ollari.CVbackend.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Interfaccia usata per estendere la classe JpaRepository e quindi ottenere tutte le funzioni utili
 * ad un'applicazione di tipo CRUD.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member as m where m.username = :un")
    Optional<Member> findByUsername(@Param("un") String username);
}
