package com.eureka.testeeureka.repository;

import com.eureka.testeeureka.model.Clients;
import com.eureka.testeeureka.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findByClientId(Long clientId);
    List<Script> findByClient(Clients client);
}
