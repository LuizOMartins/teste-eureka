package com.eureka.testeeureka.repository;

import com.eureka.testeeureka.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {
}