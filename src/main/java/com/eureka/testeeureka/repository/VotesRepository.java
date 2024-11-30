package com.eureka.testeeureka.repository;

import com.eureka.testeeureka.model.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotesRepository extends JpaRepository<Votes, Long> {
}