package com.eureka.testeeureka.repository;

import com.eureka.testeeureka.model.Clients;
import com.eureka.testeeureka.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findByClient(Clients client);

    @Query("SELECT s FROM Script s " +
            "JOIN FETCH s.workflow w " +
            "LEFT JOIN FETCH s.currentStep cs " +
            "WHERE s.client.id = :clientId")
    List<Script> findByClientId(@Param("clientId") Long clientId);

    @Query("SELECT s.id, s.content, s.workflow.id, cs.name " +
            "FROM Script s " +
            "LEFT JOIN s.currentStep cs " +
            "WHERE s.client.id = :clientId")
    List<Object[]> findScriptDetailsByClientId(@Param("clientId") Long clientId);

    @Query("""
    SELECT s FROM Script s 
    JOIN FETCH s.workflow w 
    LEFT JOIN FETCH s.currentStep cs 
    JOIN FETCH s.client c
    WHERE (:status IS NULL OR cs.name = :status)
      AND (:dateSent IS NULL OR s.createdAt = :dateSent)
      AND (:email IS NULL OR c.email = :email)
""")
    List<Script> findFilteredScripts(
            @Param("status") String status,
            @Param("dateSent") LocalDate dateSent,
            @Param("email") String email
    );


}
